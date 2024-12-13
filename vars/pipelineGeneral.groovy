def call(Map config) {
    pipeline {
        agent any // Puede ejecutarse en cualquier agente disponible

        tools {
            nodejs 'NodeJS' // Asegúrate de tener NodeJS configurado en Jenkins
        }

        stages {
            stage('Inicia el environment') {
                steps {
                    script {
                        // Calcular y asignar variables de entorno
                        env.PROJECT_NAME = config.repoUrl.tokenize('/').last().replace('.git', '')
                        env.GIT_BRANCH_1 = config.branch ?: 'main'
                        env.GIT_URL_1 = config.repoUrl ?: 'src'

                        echo "Variables de entorno inicializadas:"
                        echo "PROJECT_NAME: ${env.PROJECT_NAME}"
                        echo "GIT_BRANCH: ${env.GIT_BRANCH_1}"
                        echo "GIT_REPO_URL: ${env.GIT_URL_1}"
                    }
                }
            }

            stage('Clonar el repositorio') {
                steps {
                    script {
                        echo "Clonando el repositorio: ${env.GIT_URL_1}"
                        org.devops.lb_buildartefacto.clone()
                    }
                }
            }

            stage('Instalar depencias') {
                steps {
                    script {
                        echo "Instalando dependencias..."
                        org.devops.lb_buildartefacto.install()
                    }
                }
            }

            stage('Testeando') {
                steps {
                    script {
                        echo "Ejecutando pruebas y generando cobertura..."
                        org.devops.lb_analisissonarqube.testCoverage()
                    }
                }
            }

            stage('Analisar en sonarqube') {
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
