package io.issenn.devops.jenkins.pipeline.environment

/**
 * Constants for environment variables used by Pipeline scripts and by Jenkins
 */
class EnvironmentConstants implements Serializable {

    private static final long serialVersionUID = 1L

    //final public String SCM_URL
    //final public String TERM
    //final public String WORKSPACE
    public String BRANCH_NAME
    public String CHANGE_ID
    public String CHANGE_URL
    public String CHANGE_TITLE
    public String CHANGE_AUTHOR
    public String CHANGE_AUTHOR_DISPLAY_NAME
    public String CHANGE_AUTHOR_EMAIL
    public String CHANGE_TARGET
    public String BUILD_NUMBER
    public String BUILD_ID
    public String BUILD_DISPLAY_NAME
    public String JOB_NAME
    public String JOB_BASE_NAME
    public String BUILD_TAG
    public String EXECUTOR_NUMBER
    public String NODE_NAME
    public String NODE_LABELS
    public String WORKSPACE
    public String JENKINS_HOME
    public String JENKINS_URL
    public String BUILD_URL
    public String JOB_URL
    public String GIT_COMMIT
    public String GIT_BRANCH
    public String GIT_LOCAL_BRANCH
    public String GIT_PREVIOUS_COMMIT
    public String GIT_PREVIOUS_SUCCESSFUL_COMMIT
    public String GIT_URL
    public String GIT_URL_N
    public String GIT_AUTHOR_NAME
    public String GIT_COMMITTER_NAME
    public String GIT_AUTHOR_EMAIL
    public String GIT_COMMITTER_EMAIL

    EnvironmentConstants() {}

    EnvironmentConstants(step) {
        this.BRANCH_NAME = step.env.BRANCH_NAME
        this.CHANGE_ID = step.env.CHANGE_ID
        this.CHANGE_URL = step.env.CHANGE_URL
        this.CHANGE_TITLE = step.env.CHANGE_TITLE
        this.CHANGE_AUTHOR = step.env.CHANGE_AUTHOR
        this.CHANGE_AUTHOR_DISPLAY_NAME = step.env.CHANGE_AUTHOR_DISPLAY_NAME
        this.CHANGE_AUTHOR_EMAIL = step.env.CHANGE_AUTHOR_EMAIL
        this.CHANGE_TARGET = step.env.CHANGE_TARGET
        this.BUILD_NUMBER = step.env.BUILD_NUMBER
        this.BUILD_ID = step.env.BUILD_ID
        this.BUILD_DISPLAY_NAME = step.env.BUILD_DISPLAY_NAME
        this.JOB_NAME = step.env.JOB_NAME
        this.JOB_BASE_NAME = step.env.JOB_BASE_NAME
        this.BUILD_TAG = step.env.BUILD_TAG
        this.EXECUTOR_NUMBER = step.env.EXECUTOR_NUMBER
        this.NODE_NAME = step.env.NODE_NAME
        this.NODE_LABELS = step.env.NODE_LABELS
        this.WORKSPACE = step.env.WORKSPACE
        this.JENKINS_HOME = step.env.JENKINS_HOME
        this.JENKINS_URL = step.env.JENKINS_URL
        this.BUILD_URL = step.env.BUILD_URL
        this.JOB_URL = step.env.JOB_URL
        this.GIT_COMMIT = step.env.GIT_COMMIT
        this.GIT_BRANCH = step.env.GIT_BRANCH
        this.GIT_LOCAL_BRANCH = step.env.GIT_LOCAL_BRANCH
        this.GIT_PREVIOUS_COMMIT = step.env.GIT_PREVIOUS_COMMIT
        this.GIT_PREVIOUS_SUCCESSFUL_COMMIT = step.env.GIT_PREVIOUS_SUCCESSFUL_COMMIT
        this.GIT_URL = step.env.GIT_URL
        this.GIT_URL_N = step.env.GIT_URL_N
        this.GIT_AUTHOR_NAME = step.env.GIT_AUTHOR_NAME
        this.GIT_COMMITTER_NAME = step.env.GIT_COMMITTER_NAME
        this.GIT_AUTHOR_EMAIL = step.env.GIT_AUTHOR_EMAIL
        this.GIT_COMMITTER_EMAIL = step.env.GIT_COMMITTER_EMAIL
    }
}