package org.devops

def testCoverage() {
    sh 'npm install'  
    sh 'npm test'  
}

def analisisSonar(gitName) {
    echo "Iniciando análisis con SonarQube..."
    def scannerHome = tool 'sonar-scanner'  
    if(scannerHome) {
        withSonarQubeEnv('sonar-scanner') {  
            sh "${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=principal \
            -Dsonar.projectName=principal \
            -Dsonar.sources=. \
            -Dsonar.tests=src/__test__ \
            -Dsonar.exclusions='**/*.test.js' \
            -Dsonar.testExecutionReportPaths=./test-report.xml \
            -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info"
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}