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
                        echo "Clonando el repositorio: ${env.GIT_URL_1}"
                        org.devops.lb_buildartefacto.clone()
                    }
                }
            }
            stage('Install Dependencies') {
                steps {
                    script {
                        echo "Instalando dependencias..."
                        org.devops.lb_buildartefacto.install()
                    }
                }
            }
            stage('Run Tests and Coverage') {
                steps {
                    script {
                        echo "Ejecutando pruebas y generando cobertura..."
                        org.devops.lb_analisissonarqube.testCoverage()
                    }
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    script {
                        echo "Iniciando análisis con SonarQube..."
                        org.devops.lb_analisissonarqube.analisisSonar(env.GIT_URL_1)
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