package org.devops

def analisisOwasp(projectGitName){
    //Ejecutar el análisis OWASP con ZAP, asegurando volumen y variables correctas
    sh """ docker run --rm -v projectOwasp:/zap/wrk/:rw \
          --user root --network=${env.NameNetwork} \
          -t edansama96/zap2docker-stable \
           zap-full-scan.py \
          -t http://${env.dominio}:5174 \
           -r projectOwasp.html -I
       """
}