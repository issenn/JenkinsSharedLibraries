#!/usr/bin/env groovy

def call(String from, String to) {
    from = from == 'null' ? to : from
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
    log.info(changelogString)
    return changelogString
}

// {{hash}} {{authorName}} {{commitTime}}