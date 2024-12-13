package org.devops

def clone() {
    pipeline {
        script {
            git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
        }
    }
}