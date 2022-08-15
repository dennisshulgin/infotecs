#!/bin/bash

set -e

WORK_DIR=$(pwd)

cd "$WORK_DIR/src/main/java"
javac --release 8 -d "$WORK_DIR/bin/" com/shulgin/Main.java
cd "$WORK_DIR"
jar -cmf MANIFEST.MF application.jar -C bin .

cd "$WORK_DIR/src/test/java"
javac --release 8 -cp "$WORK_DIR/dependencies/*:$WORK_DIR/application.jar" -d "$WORK_DIR/bin-test/" com/shulgin/jsonparser/*.java com/shulgin/ftpclient/*.java com/shulgin/MainTest.java
cd "$WORK_DIR"
jar -cmf MANIFEST-TEST.MF tests.jar -C bin-test . dependencies