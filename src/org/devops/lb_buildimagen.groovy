package org.devops

def buildImageDocker(projectGitName) {
    sh "docker build -t brayanperez55/${projectGitName} ."
}