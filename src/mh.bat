@echo off

set TRACKER_PORT=10001
set TRACKER_ADDRESS=127.0.0.1:%TRACKER_PORT%

set HOST_A_PORT=10002
set HOST_A_DIR=C:\TORrent_0

set HOST_B_PORT=10003
set HOST_B_DIR=C:\TORrent_1

set HOST_C_PORT=10004
set HOST_C_DIR=C:\TORrent_2

set HOST_D_PORT=10005
set HOST_D_DIR=C:\TORrent_3

echo

echo Compiling HostTracker program
javac HostTrackerMain.java

echo Compiling Host program
javac HostMain.java

echo

echo Running HostTracker program on port %TRACKER_PORT%
START /B java HostTrackerMain %TRACKER_PORT%

echo Running Host A program on port %HOST_A_PORT%
START /B java HostMain %HOST_A_PORT% %HOST_A_DIR% %TRACKER_ADDRESS% false

echo Running Host B program on port %HOST_B_PORT%
START /B java HostMain %HOST_B_PORT% %HOST_B_DIR% %TRACKER_ADDRESS% false

echo Running Host C program on port %HOST_C_PORT%
START /B java HostMain %HOST_C_PORT% %HOST_C_DIR% %TRACKER_ADDRESS% false

echo Running Host D program on port %HOST_D_PORT%
java HostMain %HOST_D_PORT% %HOST_D_DIR% %TRACKER_ADDRESS% true