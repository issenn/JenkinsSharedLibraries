#!/usr/bin/env groovy

import org.apache.commons.lang3.StringUtils

def changeStringStyle(String str, boolean toCamel) {
    if(!str || str.size() <= 1)
        return str

    if(toCamel){
        String r = str.toLowerCase().split('_').collect{cc -> StringUtils.capitalize(cc)}.join('')
        return r
    } else {
        str = str[0].toLowerCase() + str[1..-1]
        return str.collect{cc -> ((char)cc).isUpperCase() ? '_' + cc.toLowerCase() : cc}.join('')
    }
}

def changeStringGradleStyle(String str) {
    if(!str || str.size() <= 1)
        return str

    return str[0].toUpperCase() + str[1..-1]
}

def unittest(String args='') {
    echo "Unit Testing"
    gradle "test${args}"
}

def build(String args='') {
    echo "Build args='${args}'"
    gradle "assemble${args}"
}

def artifacts(String name, String path) {
    echo "stash '${name}' '${path}'"
    stash name: "${name}", includes: "${path}"
}

def deploy(String name, String path, String targetPath) {
    echo "unstash '${name}' '${path}'"
    unstash "${name}"
    sh "mv ${WORKSPACE}/${path} ${targetPath}"
}