package org.devops

def clone() {
    pipeline {
        agent any
        stages {
            stage {
                steps {
                    script {
                        git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
                    }
                } 
            }
        }
    }
}