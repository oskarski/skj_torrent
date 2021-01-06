#!/bin/bash

TRACKER_PORT=12345
HOSTS_FILE=/Users/oskarkupski/IdeaProjects/SKJ_project/src/HostTracker/hosts.txt

kill $(lsof -t -i:$TRACKER_PORT)

echo "Compiling HostTracker program"
javac HostTrackerMain.java -d out

cd out || exit

echo "Running HostTracker program"
java HostTrackerMain ${TRACKER_PORT} ${HOSTS_FILE} &

echo "Success!"

exit 0