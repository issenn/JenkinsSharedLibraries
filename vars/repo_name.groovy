#!/usr/bin/env groovy

def call(Map args = [:]) {
    println(env,GIT_URL)
    args = [git_url: env.GIT_URL] << args
    println(args.git_url)
    def nameParts = (args.git_url - '.git').tokenize('/@')
    for (def index = 0; index < nameParts.size(); index++) {
        nameParts[index] = URLDecoder.decode(nameParts[index], 'UTF-8')
    }
    return nameParts[-1]
}