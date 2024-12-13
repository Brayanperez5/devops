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
                        // Clonación del repositorio en la rama feature
                        sh 'git clone --branch feature https://github.com/Brayanperez5/devops.git'
                        // Verificar que estamos en la rama correcta
                        sh 'cd devops && git rev-parse --abbrev-ref HEAD'
                    }
                }
            }

            stage('Traer archivos desde main') {
                steps {
                    script {
                        echo "Extrayendo archivos desde la rama main..."
                        // Realizar un fetch completo y traer los archivos necesarios
                        sh '''
                        cd devops
                        git fetch --all --prune
                        git checkout origin/main -- react-test-master/package.json react-test-master/package-lock.json
                        ls -la react-test-master
                        '''
                    }
                }
            }

            stage('Instalar dependencias') {
                steps {
                    script {
                        echo "Instalando dependencias..."
                        // Instalar dependencias dentro de la subcarpeta
                        sh 'cd devops/react-test-master && npm install'
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