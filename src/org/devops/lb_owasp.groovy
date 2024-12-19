package org.devops

def analisisOwasp(projectGitName) {
    sh """ 
    docker run -d --name owasp-analysis \
    -v projectOwasp:/zap/wrk/:rw \
    --user zap --network=${env.NameNetwork} \
    -p 8081:8080 -p 8090:8090 -i edansama96/zap2docker-stable \
    zap-webswing.sh -t ${env.dominio} -r ProjectOwasp.html -I
    """
}
