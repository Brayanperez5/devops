package org.devops

def testCoverage() {
    sh 'npm install'  
    sh 'npm test'  
}

def analisisSonar(gitName) {
    echo "Iniciando análisis con SonarQube..."
    def scannerHome = tool 'sonarScannerJenkins'  
    if(scannerHome) {
        withSonarQubeEnv('sonarScannerJenkins') {  
            sh "${scannerHome}/bin/sonarScannerJenkins -Dsonar.projectKey=${gitName} -Dsonar.projectName=${gitName} -Dsonar.sources=. -Dsonar.tests=src/__test__ -Dsonar.exclusions='**/*.test.js' -Dsonar.testExecutionReportPaths=./test-report.xml -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info"
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}