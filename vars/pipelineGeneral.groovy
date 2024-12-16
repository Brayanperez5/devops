@Library('devops@feature') _
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
                        if(lb_buildartefacto.clone()) {
                            echo 'esa era'
                        } else {
                            echo 'zzzz'
                        }
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