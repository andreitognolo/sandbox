source env.sh

ant prepare #Install PhantomJS

mvn clean install
mvn eclipse:clean eclipse:eclipse

mvn mycontainer:start
