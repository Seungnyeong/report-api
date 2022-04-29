#!/bin/bash

PID=$(cat /data/app/report-api/report-api.pid)
echo $PID

if [ ! -d $PID ] ; then
        kill -9 $PID
        echo "$PID is Killed"
        rm -rf /data/app/report-api/.pid
else
        echo "Process Already Killed"
fi