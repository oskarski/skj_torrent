#!/bin/bash

HOST_A_PORT=10006
HOST_A_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_a

HOST_B_PORT=10007
HOST_B_DIR=/Users/oskarkupski/IdeaProjects/SKJ_project/src/workspace/host_b

echo "Killing applications running on port: ${HOST_A_PORT}, ${HOST_B_PORT}"
kill $(lsof -t -i:$HOST_A_PORT)
kill $(lsof -t -i:$HOST_B_PORT)

echo ""

echo "Compiling Host program"
javac HostMain.java -d out

echo ""

cd out || exit

echo "Running Host A program on port ${HOST_A_PORT}"
java HostMain ${HOST_A_PORT} ${HOST_A_DIR} 127.0.0.1:${HOST_B_PORT} false true &

echo "Running Host B program on port ${HOST_B_PORT}"
java HostMain ${HOST_B_PORT} ${HOST_B_DIR} 127.0.0.1:${HOST_A_PORT} true true

echo "Success!"

exit 0