def lb_buildartefacto = load 'src/org/devops/lb_buildartefacto.groovy'
def lb_analisissonarqube = load 'src/org/devops/lb_analisissonarqube.groovy'

def call(Map config) {
    pipeline {
        agent any 
        tools {
            nodejs 'NodeJS' 
        }
        stages {
            stage('Clonar repositorio') {
                steps {
                    script {
                        lb_buildartefacto.clone()
                    }
                }
            }
            stage('Instalar dependencias') {
                steps {
                    script {
                        sh 'npm install'
                    }
                }
            }
            stage('Run Tests and Coverage') {
                steps {
                    script {
                        sh 'npm test'
                    }
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    script {
                        echo "Iniciando análisis con SonarQube..."
                        org.devops.lb_analisissonarqube.analisisSonar(env.GIT_BRANCH_1)
                    }
                }
            }
        }
        post {
            always {
                echo "Pipeline finalizado."
            }
            success {
                echo "Pipeline ejecutado correctamente."
            }
            failure {
                echo "El pipeline falló. Revisar los logs."
            }
        }
    }
}