#!/bin/bash

if [ "$1" != "clean" ]; then
    javac -g -d classes *.java
    javac -g -d classes test/*.java
    javac -g -d classes translator/*.java
    exit
fi

echo "rm -f classes/*.class"
echo "rm -f classes/test/*.class"
echo "rm -f classes/translator/*.class"

rm -f classes/*.class
rm -f classes/test/*.class
rm -f classes/translator/*.class
