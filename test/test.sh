#!/bin/bash

# Check the exit status of the previous command
function check_exit() {
  if [ "$?" -ne "0" ]; then
    echo "ERROR:" $1
    exit
  fi
}

# List of test files to run
files=(
"CommandLineArguments.java"
"DefaultValues.java"
"Expressions.java"
"Statements.java"
"MethodChaining.java"
"Naming.java"
"Packages.java"
"Constructors.java"
"inheritance/Super.java"
)

# Directories
main=$(pwd)
test="${main}/test"
output="${main}/output"
classes="${main}/tmp"

for file in "${files[@]}"
do
  # Parse the name of the input file
  filename=$(basename "$file")
  extension=${filename##*.}
  filename=${filename%.java}
  directory=$(dirname "$file")
  if [ "${directory}" = "." ]
  then
    fullname="${filename}"
  else
    fullname="${directory}/${filename}"
  fi
# if directory == ., don't prepend it

  echo
  echo "#################################################################"
  echo "#################################################################"
  echo
  echo "Testing ${fullname}.java ... "

  # Run our translator on input file and generate C++ output
  cd "${main}"
  echo
  echo "#################################################################"
  echo "#################################################################"
  echo
  echo "Translating Java to C++ ... "
  ant -quiet translate -Dfile="${test}/$file"
  check_exit "${test}/$file cannot be translated."

  # Run the Java input
  cd "${test}"
  echo
  echo "#################################################################"
  echo
  echo "Running Java ... "
  echo
  java -cp "${classes}" "${fullname}" "arg1" "arg2" "arg3"
  check_exit "${filename}.java does not run correctly."

  # Compile the generated C++ code
  cd "${output}"
  g++ *.cc include/*.cc -o ./${filename}
  check_exit "${filename} does not compile."

  # Run the compiled C++ code
  echo
  echo "#################################################################"
  echo
  echo "Running C++ ..."
  echo
  ./${filename} arg1 arg2 arg3
  check_exit "${filename} does not run correctly."

done
