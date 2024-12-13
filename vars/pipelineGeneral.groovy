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
            stage('Preparar entorno') {
                steps {
                    script {
                        checkout([$class: 'GitSCM', branches: [[name: '*/feature']], userRemoteConfigs: [[url: 'https://github.com/Brayanperez5/devops.git']]])
                    }
                    sh 'git rev-parse --abbrev-ref HEAD'
                }
            }
            stage('Traer archivos desde main') {
                steps {
                    script {
                        // Traer los archivos específicos desde la rama main
                        sh '''
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