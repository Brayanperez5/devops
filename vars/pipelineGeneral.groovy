def lb_buildartefacto
def lb_analisissonarqube
def call(Map config) {
node {
    try {
        // Cargar las librerías desde la ruta correcta
        stage('Cargar Librerías') {
            script {
                echo "Cargando las librerías..."
                lb_buildartefacto = load '../src/org/devops/lb_buildartefacto.groovy'
                lb_analisissonarqube = load '../src/org/devops/lb_analisissonarqube.groovy'
            }
        }
        stage('Clonar Repositorio') {
            script {
                lb_buildartefacto.clone()
            }
        }
        // Ejecutar las etapas utilizando las librerías cargadas
        stage('Instalar Dependencias') {
            script {
                echo "Instalando dependencias..."
                lb_buildartefacto.install() // Llamar la función desde la librería
            }
        }

        stage('Ejecutar Pruebas') {
            script {
                echo "Ejecutando pruebas..."
                lb_analisissonarqube.testCoverage() // Llamar la función desde la librería
            }
        }

        stage('SonarQube Analysis') {
            script {
                echo "Iniciando análisis con SonarQube..."
                lb_analisissonarqube.analisisSonar(env.GIT_BRANCH_1) // Llamar a SonarQube
            }
        }
    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo "Pipeline completado."
    }
}
}