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
                        // Asegúrate de clonar el repositorio correctamente
                        sh 'git clone --branch feature https://github.com/Brayanperez5/devops.git'
                        // Verifica que estamos en la rama correcta
                        sh 'cd devops && git rev-parse --abbrev-ref HEAD'
                    }
                }
            }

            stage('Traer archivos desde main') {
                steps {
                    script {
                        // Extraer los archivos necesarios desde la rama main
                        sh '''
                        cd devops
                        git fetch origin main
                        git checkout origin/main -- package.json package-lock.json
                        ls -la
                        '''
                    }
                }
            }

            stage('Instalar dependencias') {
                steps {
                    script {
                        echo "Instalando dependencias..."
                        // Asegúrate de ejecutar npm install dentro del directorio del proyecto
                        sh 'cd devops && npm install'
                    }
                }
            }

            stage('Run Tests and Coverage') {
                steps {
                    script {
                        echo "Ejecutando pruebas y generando cobertura..."
                        // Llamada a tu librería para ejecutar pruebas
                        org.devops.lb_analisissonarqube.testCoverage()
                    }
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        echo "Iniciando análisis con SonarQube..."
                        // Llamada a la librería para análisis SonarQube
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
