#!/bin/bash

export MAVEN_HOME=$(pwd)/gen/app/maven
export PATH=$MAVEN_HOME/bin:$(pwd)/gen/app/google-cloud-sdk/bin:$PATH
alias mvn="$MAVEN_HOME/bin/mvn -s $(pwd)/env/ci/settings.xml"

source cmds/staging/env.sh


