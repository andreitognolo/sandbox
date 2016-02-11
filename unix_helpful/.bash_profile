BASH_LOAD_ROOT=/Users/atognolo/projs/bash
. ${BASH_LOAD_ROOT}/load_bash

# rbenv
export PATH="$HOME/.rbenv/bin:$PATH"
eval "$(rbenv init -)"

# nvm
export NVM_DIR=~/.nvm
source $(brew --prefix nvm)/nvm.sh

# snap command line tool
export PATH=/Users/atognolo/projs/snap/go-saas-chef/scripts:$PATH

# snap utils
export PATH=/Users/atognolo/projs/snap/utils:$PATH

# alias
alias be="bundle exec"
alias g="git"
alias vg="vagrant"

# java
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_31.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

# maven
export PATH=/Users/atognolo/apps/apache-maven-3.3.3/bin:$PATH

### Added by the Heroku Toolbelt
export PATH="/usr/local/heroku/bin:$PATH"

# Git branch in prompt.
parse_git_branch() {
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/ (\1)/'
}
BLUE='\e[0;35m'
ENDCOLOR="\e[0m"
export PS1="-> $BLUE\W$ENDCOLOR\[\033[32m\]\$(parse_git_branch)\[\033[00m\] \n$ "

export LC_ALL=en_US.UTF-8
export LANG=en_US.UTF-8
