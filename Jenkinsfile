@Library('devops@feature') _
pipeline{
    stages {
        stage('Instalar dependencias') {
                steps {
                    script {
                        org.devops.lb_buildartefacto.install()
                    }
                }
    }
}
}