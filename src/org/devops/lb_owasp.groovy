package org.devops

def analisisOwasp(projectGitName){
    sh """ 
        docker run -d --name analisis-owasp \
        -v projectOwasp:/zap/wrk/:rw \
        --user root --network=${env.NameNetwork} \
        -p 8081:8080 -p 8090:8090 -i edansama96/zap2docker-stable \
        zap-full-scan.py -t ${env.dominio} -r ProjectOwasp.html -I
    """
}