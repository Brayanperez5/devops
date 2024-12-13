package org.devops

// Función para clonar el repositorio
def clone() {
    echo "Clonando el repositorio desde: ${env.GIT_URL_1}"
    git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
}
