#!/usr/bin/env groovy

def call(String from, String to) {
    println(from)
    println(to)
    from = from == 'null' ? to : from
    println(from)
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