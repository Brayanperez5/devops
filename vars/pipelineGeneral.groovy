    pipeline {
        agent any 

        tools {
            nodejs 'NodeJS' 
        }

        stages {
            stage('Clonar el repositorio') {
                steps {
                    script {
                        org.devops.lb_buildartefacto.clone()
                    }
                }
            }

            stage('Instalar depencias') {
                steps {
                    script {
                        org.devops.lb_buildartefacto.install()
                    }
                }
            }

            stage('Testeando') {
                steps {
                    script {
                        org.devops.lb_analisissonarqube.testCoverage()
                    }
                }
            }

            stage('Analisar en sonarqube') {
                steps {
                    script {
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