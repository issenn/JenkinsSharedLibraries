#!/usr/bin/env groovy

def call(String from, String to) {
    def changelogString = gitChangelog returnType: 'STRING',
    from: [type: 'COMMIT', value: "${from}"],
    to: [type: 'COMMIT', value: "${to}"],
    template: '''
{{#messageBodyItems}}
<li> {{.}}</li>
{{/messageBodyItems}}'''
    return changelogString
}

// {{hash}} {{authorName}} {{commitTime}}