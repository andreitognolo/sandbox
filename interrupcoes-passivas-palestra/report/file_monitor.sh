#!/bin/bash -e

if [ "x$1" == "x" -o "x$2" == "x" ]; then
        echo "Script usage:"
        echo "./file-monitor.sh <file> <script.sh>" 
        exit 1;
fi;

while true
do
   ATIME=`stat -t %T $1`
   if [[ "$ATIME" != "$LTIME" ]]
   then
		echo "Change detected"
		
		if [ -f /tmp/file_monitor_result ]; then
			rm /tmp/file_monitor_result	
		fi
		
		source $2 || 
		time=$(date +%T)
		terminal-notifier -message "Change detected"
       LTIME=$ATIME
   fi
   sleep 1
done
