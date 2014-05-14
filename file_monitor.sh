#!/bin/bash -e
while true
do
   ATIME=`stat -c %Z $1`
   if [[ "$ATIME" != "$LTIME" ]]
   then
		echo "Change detected"
		
		if [ -f /tmp/file_monitor_result ]; then
			rm /tmp/file_monitor_result	
		fi
		
		result=$(sh $2)
		time=$(date +%T)
		if [ "$result" = "" ]; then
    		notify-send "$time - Alteracao realizada"
		else
			echo "ERROR - $result"
			notify-send "$time - ERROR: $result"
		fi
    	
       LTIME=$ATIME
   fi
   sleep 5
done
