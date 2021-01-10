@echo off

set HOST_A_PORT=10006
set HOST_A_DIR=C:\TORrent_0

set HOST_B_PORT=10007
set HOST_B_DIR=C:\TORrent_1

echo

echo Compiling Host program
javac HostMain.java

echo

echo Running Host A program on port %HOST_A_PORT%
START /B java HostMain %HOST_A_PORT% %HOST_A_DIR% 127.0.0.1:%HOST_B_PORT% false true

echo Running Host B program on port %HOST_B_PORT%
java HostMain %HOST_B_PORT% %HOST_B_DIR% 127.0.0.1:%HOST_A_PORT% true true
