#!/usr/bin/env groovy

def call(Map args = [:]) {
    args = [git_url: env.GIT_URL] << args
    def nameParts = (args.git_url - '.git').tokenize('/@')
    for (def index = 0; index < nameParts.size(); index++) {
        nameParts[index] = URLDecoder.decode(nameParts[index], 'UTF-8')
    }
    return nameParts[-1]
}