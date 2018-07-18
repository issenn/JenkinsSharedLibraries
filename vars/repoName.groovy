#!/usr/bin/env groovy

import java.net.URLDecoder;

def call(script, String gitUrl='') {
    gitUrl = gitUrl ?: "${script.env.GIT_URL}"
    println(gitUrl)
    println("${script.env.GIT_URL}")
    //def nameParts = (args.git_url - '.git').tokenize('/@')
    //for (def index = 0; index < nameParts.size(); index++) {
    //    nameParts[index] = URLDecoder.decode(nameParts[index], 'UTF-8')
    //}
    //return nameParts[-1]
}