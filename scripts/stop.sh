#!/bin/bash

PID=`ps -eaf | grep report-api| grep -v grep | awk '{print $2}'`
echo $PID

if [ ! -d $PID ] ; then
        kill -9 $PID
        echo "$PID is Killed"
else
        echo "Process Already Killed"
fi