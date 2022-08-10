#!/bin/bash

set -e

cd ./src/main/java
javac --release 8 -d ../../../bin/ com/shulgin/Main.java
cd ./../../../
jar -cmf META-INF/MANIFEST.MF application.jar -C bin .
#java -jar application.jar