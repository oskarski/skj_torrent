#!/bin/bash

TRACKER_PORT=12345
HOSTS_FILE=/Users/oskarkupski/IdeaProjects/SKJ_project/src/HostTracker/hosts.txt
TRACKER_ADDRESS=127.0.0.1:${TRACKER_PORT}

HOST_A_PORT=12346
HOST_A_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_a

HOST_B_PORT=12347
HOST_B_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_b

HOST_C_PORT=12348
HOST_C_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_z


echo "Killing applications running on port: ${TRACKER_PORT}, ${HOST_A_PORT}, ${HOST_B_PORT}"
kill $(lsof -t -i:$TRACKER_PORT)
kill $(lsof -t -i:$HOST_A_PORT)
kill $(lsof -t -i:$HOST_B_PORT)

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
java HostMain ${HOST_A_PORT} ${HOST_A_DIR} ${TRACKER_ADDRESS} false &

sleep 1

echo "Running Host B program"
java HostMain ${HOST_B_PORT} ${HOST_B_DIR} ${TRACKER_ADDRESS} false &
sleep 1

echo "Running Host C program"
java HostMain ${HOST_C_PORT} ${HOST_C_DIR} ${TRACKER_ADDRESS} true

echo "Success!"

exit 0