#!/usr/bin/env groovy

def call(String from, String to) {
    println(from)
    println(to)
    def aaa = from ?: '123'
    println(aaa)
    def changelogString = gitChangelog returnType: 'STRING',
    from: [type: 'COMMIT', value: "${from}"],
    to: [type: 'COMMIT', value: "${to}"],
    template: '''
{{#commits}}
{{{messageTitle}}}
{{#messageBodyItems}}
<li> {{.}}</li>
{{/messageBodyItems}}
{{/commits}}'''
    return changelogString
}

// {{hash}} {{authorName}} {{commitTime}}