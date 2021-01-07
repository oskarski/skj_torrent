#!/bin/bash

TRACKER_PORT=12345
HOSTS_FILE=/Users/oskarkupski/IdeaProjects/SKJ_project/src/HostTracker/hosts.txt
TRACKER_ADDRESS=127.0.0.1:${TRACKER_PORT}

HOST_A_PORT=12346
HOST_A_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/host_a
HOST_A_RUN_UI=false


echo "Killing applications running on port: ${TRACKER_PORT}, ${HOST_A_PORT}"
kill $(lsof -t -i:$TRACKER_PORT)
kill $(lsof -t -i:$HOST_A_PORT)

echo ""

echo "Compiling HostTracker program"
javac HostTrackerMain.java -d out

echo "Compiling Host program"
javac HostMain.java -d out

echo ""

cd out || exit

echo "Running HostTracker program"
java HostTrackerMain ${TRACKER_PORT} ${HOSTS_FILE} &

echo "Running Host A program"
java HostMain ${HOST_A_PORT} ${HOST_A_DIR} ${TRACKER_ADDRESS} ${HOST_A_RUN_UI} &

echo "Success!"

exit 0