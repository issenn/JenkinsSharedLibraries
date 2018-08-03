#!/usr/bin/env groovy

def call(String from, String to) {
    def changelogString = gitChangelog returnType: 'STRING',
    from: [type: 'COMMIT', value: "${from}"],
    to: [type: 'COMMIT', value: "${to}"],
    template: '''
<h1> Git Changelog changelog </h1>
Changelog of Git Changelog.
{{#tags}}
{{#commits}}
{{{messageTitle}}}
{{#messageBodyItems}}
<li> {{.}}</li>
{{/messageBodyItems}}
{{/commits}}
{{/tags}}'''
    println(changelogString)
    return changelogString
}

// {{hash}} {{authorName}} {{commitTime}}