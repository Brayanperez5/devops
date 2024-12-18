package org.devops

def buildImageDocker() {
    sh "docker build -t brayanperez55/${projectGitName} ."
}