#!/bin/bash

TRACKER_PORT=12345

kill $(lsof -t -i:$TRACKER_PORT)

echo "Compiling HostTracker program"
javac HostTrackerMain.java -d out

cd out || exit

echo "Running HostTracker program"
java HostTrackerMain ${TRACKER_PORT} &

echo "Success!"

exit 0