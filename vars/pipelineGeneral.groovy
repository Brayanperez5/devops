import org.devops.lb_buildimagen
import org.devops.lb_publicardockerhub
import org.devops.lb_deploydocker
import org.devops.lb_owasp

def call(Map config) {
    pipeline {
        agent any
        tools {
            nodejs ('NodeJS')
        }
        stages {
            stage {
                steps {
                    script {
                        lb_buildimagen.buildImageDocker()
                    }
                }
            }
            stage {
                steps {
                    script {
                        lb_publicardockerhub.publicarImage()
                    }
                }
            }
            stage {
                steps {
                    script {
                        lb_deploydocker.despliegueContenedor()
                    }
                }
            }
            stage {
                steps {
                    script {
                        lb_owasp.analisisOwasp()
                    }
                }
            }
        } 
    }
}