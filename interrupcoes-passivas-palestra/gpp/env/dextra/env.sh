#!/bin/bash

shopt -s expand_aliases

export MAVEN_HOME=$(pwd)/gen/app/maven
export PATH=$MAVEN_HOME/bin:$(pwd)/gen/app/google-cloud-sdk/bin:$PATH

#alias ant="ant -DproxyHost=172.16.129.4 -DproxyPort=3128 -DproxyUser=internal -DproxyPass=external"
alias mvn="$MAVEN_HOME/bin/mvn -s $(pwd)/env/ci/settings.xml"

source cmds/staging/env.sh


