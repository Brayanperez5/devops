package org.devops

def testCoverage() {
    sh 'npm test -- --coverage'
}

def analisisSonar(gitName) {
    def scannerHome = tool 'sonar-scanner'
    if(scannerHome) {
        withSonarQubeEnv('sonar-scanner') {
            sh "${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectkey=${gitName} \
            -Dsonar.projectName=${gitName} \
            -Dsonar.sources=${env.source} \
            -Dsonar.tests=src/__test__ \
            -Dsonar.exclusions='**/*.test.js' \
            -Dsonar.testExecutionReportPaths=./test-report.xml \
            -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info"
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}