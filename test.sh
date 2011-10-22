#!/bin/bash

# check exit status of previous command. exit outputting input message if there was an error
function check_exit() {
    if [ "$?" -ne "0" ]; then
        echo "ERROR:" $1
        rm -f ${java_file}.class a.out java_output.txt cpp_output.txt
        exit
    fi
}
java_file=${1%.java}

javac Translator.java
check_exit "Translator.java doesn't compile"

javac $1
check_exit "the input file doesn't compile"

# TODO: currently, stdout here (i.e. contents of $cpp_file) is not actually pure c++ code. make sure it is, or else modify this script
cpp_file=${1%.java}.cc
java Translator -translateJava $1 > $cpp_file
check_exit "the input file compiles, but Translator.java can't accept it"

# TODO: add as many command line args (starting from $2) here as might be passed to the java file
java "$java_file" > java_output.txt
check_exit "the input file compiles, but doesn't run correctly"

g++ "$cpp_file"
check_exit "the translated c++ file doesn't compile"

./a.out > cpp_output.txt
check_exit "the translated c++ file compiles, but doesn't run correctly"

diff java_output.txt cpp_output.txt > ${java_file}_errors.txt
rm ${java_file}.class a.out java_output.txt cpp_output.txt
