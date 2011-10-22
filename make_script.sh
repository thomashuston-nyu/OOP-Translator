#!/bin/bash

if [ "$1" != "clean" ]; then
    javac -g -d classes *.java
    javac -g -d classes test/*.java
    javac -g -d classes translator/*.java
    exit
fi

echo "rm -f *.class"
echo "rm -f test/*.class"
echo "rm -f translator/*.class"

rm -f *.class
rm -f test/*.class
rm -f translator/*.class
