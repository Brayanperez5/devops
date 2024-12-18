package org.devops

def buildImageDocker() {
    sh "sudo docker build -t brayanperez55/${projectGitName} ."
}