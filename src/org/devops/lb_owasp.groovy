package org.devops

def analisisOwasp(projectGitName) {
    sh """ docker run --rm -v projectOwasp:/zap/wrk/:rw \
    --user root --network=${env.NameNetwork} \
    -t owasp/zap2docker-stable \
    -t ${env.dominio} \
    -r ProjectOwasp.hmtl -I
    """
}