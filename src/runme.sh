#!/bin/bash

TRACKER_PORT=12345

kill $(lsof -t -i:$TRACKER_PORT)

echo "Compiling Tracker program"
javac TrackerMain.java -d out

cd out || exit

echo "Running Tracker program"
java TrackerMain ${TRACKER_PORT} &

echo "Success!"

exit 0