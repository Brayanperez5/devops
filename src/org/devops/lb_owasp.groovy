package org.devops

def analisisOwasp(projectGitName){
    sh """ docker run --rm -v /path/to/local/ProjectOwasp:/zap/wrk/:rw \
          --user root --network=${env.NameNetwork} \
          -t edansama96/zap2docker-stable \
           zap-full-scan.py \
          -t ${env.dominio} \
           -r /zap/wrk/ProjectOwasp.html -I
       """
}