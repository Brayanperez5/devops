import org.devops.lb_buildimagen
import org.devops.lb_publicardockerhub
import org.devops.lb_deploydocker
import org.devops.lb_owasp

def call(Map config) {
    def lb_buildimagen = new lb_buildimagen()
    def lb_publicardockerhub = new lb_publicardockerhub()
    def lb_deploydocker = new lb_deploydocker()
    def lb_owasp = new lb_owasp()
    pipeline {
        agent any
        tools {
            nodejs ('NodeJS')
        }
        stages {
            stage('construccion imagen') {
                steps {
                    script {
                        lb_buildimagen.buildImageDocker()
                    }
                }
            }
            stage('publicacion en docker') {
                steps {
                    script {
                        lb_publicardockerhub.publicarImage()
                    }
                }
            }
            stage('despliegue en contenedor') {
                steps {
                    script {
                        lb_deploydocker.despliegueContenedor()
                    }
                }
            }
            stage('analisis en owasp') {
                steps {
                    script {
                        lb_owasp.analisisOwasp()
                    }
                }
            }
        } 
    }
}