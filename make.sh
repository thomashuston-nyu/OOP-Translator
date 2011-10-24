#!/bin/bash


if [ "$1" == "clean" ]; then
    echo "rm -f classes/*.class"
    echo "rm -f classes/test/*.class"
    echo "rm -f classes/translator/*.class"
    rm -f classes/*.class
    rm -f classes/test/*.class
    rm -f classes/translator/*.class
    exit
fi

if [ "$1" == "test" ]; then
    cd classes
    java Translator -translateJava "../"$2
    cd ..
    exit
fi

javac -g -d classes *.java
javac -g -d classes test/*.java
