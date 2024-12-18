import org.devops.lb_buildimagen
import org.devops.lb_publicardockerhub
import org.devops.lb_deploydocker
import org.devops.lb_owasp

def call(Map config) {
    def lb_buildimagen = new lb_buildimagen()
    pipeline {
        agent any
        tools {
            nodejs ('NodeJS')
        }
        stages {
            stage('Extract Project Name') {
                steps {
                    script {
                            def urlGitHub = sh(script: 'git config --get remote.origin.url', returnStdout: true).trim()
                            echo "URL del repositorio Git: ${urlGitHub}"

                            def projectGitName = urlGitHub.replaceAll(/^.*\/([^\/]+)\.git$/, '$1')
                            echo "Nombre del proyecto extraído: ${projectGitName}"
                            
                            env.projectGitName = projectGitName
                        }
                    }
            }
            stage('construccion imagen') {
                steps {
                    script {
                        lb_buildimagen.buildImageDocker(env.projectGitName)
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