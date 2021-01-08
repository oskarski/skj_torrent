#!/bin/bash

TRACKER_PORT=10001
TRACKER_ADDRESS=127.0.0.1:${TRACKER_PORT}

HOST_A_PORT=10002
HOST_A_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_a

HOST_B_PORT=10003
HOST_B_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_b

HOST_C_PORT=10004
HOST_C_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_c

HOST_D_PORT=10005
HOST_D_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_d


echo "Killing applications running on port: ${TRACKER_PORT}, ${HOST_A_PORT}, ${HOST_B_PORT}, ${HOST_C_PORT}, ${HOST_D_PORT}"
kill $(lsof -t -i:$TRACKER_PORT)
kill $(lsof -t -i:$HOST_A_PORT)
kill $(lsof -t -i:$HOST_B_PORT)
kill $(lsof -t -i:$HOST_C_PORT)
kill $(lsof -t -i:$HOST_D_PORT)

echo ""

echo "Compiling HostTracker program"
javac HostTrackerMain.java -d out

echo "Compiling Host program"
javac HostMain.java -d out

echo ""

cd out || exit

echo "Running HostTracker program on port ${TRACKER_PORT}"
java HostTrackerMain ${TRACKER_PORT} &

echo "Running Host A program on port ${HOST_A_PORT}"
java HostMain ${HOST_A_PORT} ${HOST_A_DIR} ${TRACKER_ADDRESS} false &

echo "Running Host B program on port ${HOST_B_PORT}"
java HostMain ${HOST_B_PORT} ${HOST_B_DIR} ${TRACKER_ADDRESS} false &

echo "Running Host C program on port ${HOST_C_PORT}"
java HostMain ${HOST_C_PORT} ${HOST_C_DIR} ${TRACKER_ADDRESS} false &

echo "Running Host D program on port ${HOST_D_PORT}"
java HostMain ${HOST_D_PORT} ${HOST_D_DIR} ${TRACKER_ADDRESS} true

echo "Success!"

exit 0