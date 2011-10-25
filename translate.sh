#!/bin/bash

# Check the exit status of the previous command
function check_exit() {
    if [ "$?" -ne "0" ]; then
        echo "ERROR:" $1
        exit
    fi
}

# Parse the name of the input file
filename=$(basename "$1")
extension=${filename##*.}
filename=${filename%.java}
directory=$(dirname "$1")
current=$(pwd)

# Run our translator on input file $1 and generate C++ output
java -jar translator.jar -translateJava $1
check_exit "${filename}.java cannot be translated."

# Compile the Java input
echo
echo "Compiling ${filename}.java ... "
cd "${current}/${directory}"
javac ${filename}.java
check_exit "${filename}.java does not compile."

# Run the Java input
echo
echo "Running ${filename}.java ... "
java ${filename}
check_exit "${filename}.java does not run correctly."
cd "${current}"

# Compile the generated C++ code
echo
echo "Compiling ${filename}.cc ..."
g++ ${filename}.cc java_lang/java_lang.cc -o ./${filename}
check_exit "${filename}.cc does not compile."

# Run the compiled C++ code
echo
echo "Running ${filename}.cc ..."
./${filename}
check_exit "${filename}.cc does not run correctly."