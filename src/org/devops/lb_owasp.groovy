package org.devops

def analisisOwasp(projectGitName) {
    sh """ 
    docker run -d --name owasp-analysis \
    -v projectOwasp:/zap/wrk/:rw \
    --user zap --network=${env.NameNetwork} \
    -p 8081:8080 -p 8090:8090 -i edansama96/zap2docker-stable \
    zap-webswing.sh -t ${env.dominio} -r /zap/wrk/ProjectOwasp.html -I
    """
    
    // Esperar a que el contenedor finalice su trabajo
    sh "docker wait owasp-analysis"
    
    // Verificar si el reporte fue generado
    sh """
    if [ ! -f /zap/wrk/ProjectOwasp.html ]; then
        echo "El reporte no fue generado. Revisa los parámetros o el volumen."
        docker logs owasp-analysis
        exit 1
    fi
    """
    echo "Reporte generado correctamente en el volumen projectOwasp."
}