package org.devops

def clone() {
    git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
}
def install() {
    sh 'ls -la'
    sh 'npm install --verbose'
}
