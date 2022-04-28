#!/bin/bash

PID=$(cat report-api.pid)
echo $PID

if [ ! -d $PID ] ; then
        kill -9 $PID
        echo "$PID is Killed"
        rm -rf report-api.pid
else
        echo "Process Already Killed"
fi