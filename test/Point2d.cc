Java to C++ Translator, v. 2.1.1, The Allan Gottlieb Fan Club
Processing test/Point2d.java ...
#include <iostream>
#include <sstream>

#include "../java/java_lang.h"

using namespace java::lang;

namespace test {

  struct __Point2d;
  struct __Point2d_VT;

  typedef __Point2d* Point2d;

  struct __Point2d {
    __Point2d_VT* __vptr;
    double x;
    double y;
  };

  struct __Point2d_VT {
  };


}
