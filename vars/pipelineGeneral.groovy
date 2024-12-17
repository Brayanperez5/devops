import org.devops.lb_buildartefacto
import org.devops.lb_analisissonarqube

def call(Map config) {
    def lb_buildartefacto = new lb_buildartefacto()
    def lb_analisissonarqube = new lb_analisissonarqube()
    pipeline {
        agent any 
        tools {
            nodejs 'NodeJS' 
        }
        environment {
            SONAR_SCANNER_HOME = tool 'sonar-scanner'
        }
        stages {
            stage('Clonar repositorio') {
                steps {
                    script {
                        lb_buildartefacto.clone()
                    }
                }
            }
            stage('Correr el test para analisis en sonarqube') {
                steps {
                    script {
                        lb_analisissonarqube.testCoverage()
                    }
                }
            }
            stage('Probar conexión SonarQube') {
                steps {
                    def scannerHome = tool 'sonar-scanner' 
                    if(scannerHome) {
                        withSonarQubeEnv('sonar-scanner') { // Usar el nombre del servidor configurado en Jenkins
                            echo "Probando conexión con SonarQube..."
                            sh '''
                            ${SONAR_SCANNER_HOME}/bin/sonar-scanner \
                            -Dsonar.projectKey=test-sonarqube \
                            -Dsonar.projectName="Prueba de Conexión SonarQube" \
                            -Dsonar.sources=. \
                            -Dsonar.host.url=$SONAR_HOST_URL \
                            -Dsonar.login=$SONAR_AUTH_TOKEN \
                            -X
                            '''
                        }
                    }
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    script {
                        lb_analisissonarqube.analisisSonar(env.GIT_BRANCH_1)
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