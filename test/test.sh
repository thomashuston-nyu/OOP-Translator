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
    "test/While.java"
    "xtc/oop/Test.java"
)

# Directories
main=$(pwd)
test="${main}/test"
output="${main}/output"
classes="${main}/temp"

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
  echo "Testing ${test}/$file"

  # Compile the Java input
  cd "${test}"
#javac "${fullname}.java"
#  check_exit "${test}/$file does not compile."

  # Run the Java input
  echo
  echo "Running Java ... "
  echo
  java -cp "${classes}" "${fullname}"
  check_exit "${filename}.java does not run correctly."

  # Run our translator on input file $1 and generate C++ output
  cd "${main}"
  ant -quiet translate -Dfile="${test}/$file"
  check_exit "${test}/$file cannot be translated."

  # Compile the generated C++ code
  cd "${output}"
  g++ *.cc include/*.cc -o ./${filename}
  check_exit "${filename} does not compile."

  # Run the compiled C++ code
  echo
  echo "Running C++ ..."
  echo
  ./${filename}
  check_exit "${filename} does not run correctly."

  echo
done
#g++ output/*.cc output/include/*.cc
