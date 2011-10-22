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

javac "$1"
check_exit "the input file doesn't compile"

# TODO: add as many command line args (starting from $2) here as might be passed to the java file
java "$java_file" > java_output.txt
check_exit "the input file compiles, but doesn't run correctly"

# TODO: make sure that generated c++ code doesn't include any extraneous, illegal lines. (for example, see sed command below)
cpp_file=${1%.java}.cc
java Translator -translateJava $1 > $cpp_file
check_exit "the input file compiles and runs, but Translator.java can't accept it"

# remove xtc output that isn't valid c++ code
sed -i '1,2d' "$cpp_file"

g++ "$cpp_file"
check_exit "the translated c++ file doesn't compile"

./a.out > cpp_output.txt
check_exit "the translated c++ file compiles, but doesn't run correctly"

diff java_output.txt cpp_output.txt > ${java_file}_errors.txt
rm ${java_file}.class a.out java_output.txt cpp_output.txt
echo "diff between java output and c++ output is in ${java_file}_errors.txt"
