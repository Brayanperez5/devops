package org.devops

def buildImageDocker(projectGitName) {
    bat "docker build -t brayanperez55/${projectGitName} ."
}