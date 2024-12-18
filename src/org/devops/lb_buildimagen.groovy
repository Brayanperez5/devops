package org.devops

def buildImageDocker() {
    bat "docker build -t brayanperez55/${projectGitName} ."
}