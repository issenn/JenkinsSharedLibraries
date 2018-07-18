package io.issenn

import net.sf.json.JSONObject;

/**
 * Constants for environment variables used by Pipeline scripts and by Jenkins
 */
class EnvironmentConstants implements Serializable {

    private static final long serialVersionUID = 1L

    //final public String SCM_URL
    //final public String TERM
    //final public String WORKSPACE
    final public String BRANCH_NAME
    final public String CHANGE_ID
    final public String CHANGE_URL
    final public String CHANGE_TITLE
    final public String CHANGE_AUTHOR
    final public String CHANGE_AUTHOR_DISPLAY_NAME
    final public String CHANGE_AUTHOR_EMAIL
    final public String CHANGE_TARGET
    final public String BUILD_NUMBER
    final public String BUILD_ID
    final public String BUILD_DISPLAY_NAME
    final public String JOB_NAME
    final public String JOB_BASE_NAME
    final public String BUILD_TAG
    final public String EXECUTOR_NUMBER
    final public String NODE_NAME
    final public String NODE_LABELS
    final public String WORKSPACE
    final public String JENKINS_HOME
    final public String JENKINS_URL
    final public String BUILD_URL
    final public String JOB_URL
    final public String GIT_COMMIT
    final public String GIT_BRANCH
    final public String GIT_LOCAL_BRANCH
    final public String GIT_PREVIOUS_COMMIT
    final public String GIT_PREVIOUS_SUCCESSFUL_COMMIT
    final public String GIT_URL
    final public String GIT_URL_N
    final public String GIT_AUTHOR_NAME
    final public String GIT_COMMITTER_NAME
    final public String GIT_AUTHOR_EMAIL
    final public String GIT_COMMITTER_EMAIL

    EnvironmentConstants() {}

    EnvironmentConstants(script) {
        this.BRANCH_NAME = script.env.BRANCH_NAME
        /*this.CHANGE_ID = environment.CHANGE_ID
        this.CHANGE_URL = environment.CHANGE_URL
        this.CHANGE_TITLE = environment.CHANGE_TITLE
        this.CHANGE_AUTHOR = environment.CHANGE_AUTHOR
        this.CHANGE_AUTHOR_DISPLAY_NAME = environment.CHANGE_AUTHOR_DISPLAY_NAME
        this.CHANGE_AUTHOR_EMAIL = environment.CHANGE_AUTHOR_EMAIL
        this.CHANGE_TARGET = environment.CHANGE_TARGET
        this.BUILD_NUMBER = environment.BUILD_NUMBER
        this.BUILD_ID = environment.BUILD_ID
        this.BUILD_DISPLAY_NAME = environment.BUILD_DISPLAY_NAME
        this.JOB_NAME = environment.JOB_NAME
        this.JOB_BASE_NAME = environment.JOB_BASE_NAME
        this.BUILD_TAG = environment.BUILD_TAG
        this.EXECUTOR_NUMBER = environment.EXECUTOR_NUMBER
        this.NODE_NAME = environment.NODE_NAME
        this.NODE_LABELS = environment.NODE_LABELS
        this.WORKSPACE = environment.WORKSPACE
        this.JENKINS_HOME = environment.JENKINS_HOME
        this.JENKINS_URL = environment.JENKINS_URL
        this.BUILD_URL = environment.BUILD_URL
        this.JOB_URL = environment.JOB_URL
        this.GIT_COMMIT = environment.GIT_COMMIT
        this.GIT_BRANCH = environment.GIT_BRANCH
        this.GIT_LOCAL_BRANCH = environment.GIT_LOCAL_BRANCH
        this.GIT_PREVIOUS_COMMIT = environment.GIT_PREVIOUS_COMMIT
        this.GIT_PREVIOUS_SUCCESSFUL_COMMIT = environment.GIT_PREVIOUS_SUCCESSFUL_COMMIT
        this.GIT_URL = environment.GIT_URL
        this.GIT_URL_N = environment.GIT_URL_N
        this.GIT_AUTHOR_NAME = environment.GIT_AUTHOR_NAME
        this.GIT_COMMITTER_NAME = environment.GIT_COMMITTER_NAME
        this.GIT_AUTHOR_EMAIL = environment.GIT_AUTHOR_EMAIL
        this.GIT_COMMITTER_EMAIL = environment.GIT_COMMITTER_EMAIL*/
    }

    static getEnv() {
        return "123"
    }
}