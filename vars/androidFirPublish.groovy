#!/usr/bin/env groovy

def call(String path, String changelog) {
    script {
        sh "sudo /usr/local/bin/fir publish -T 9611b6a99d280463039cbb64b7eb24ca -c '${changelog}' ${path}"
        // sh "sudo /usr/local/bin/fir publish -T 7d9e8accc5f39e99017032925990c57f -c '${changelog}' ${path}"
        // sh("sudo /usr/local/bin/fir publish -T db412ee63c68dcce0f9be05d0e40025c -c ${changelog} ${path}")
    }
}
