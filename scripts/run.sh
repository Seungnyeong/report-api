#!/bin/bash

nohup java -jar -Dspring.profiles.active=dev /data/app/report-api/build/libs/report-api-1.0.jar > /dev/null 2>&1 &