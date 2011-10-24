#pragma once

#include <iostream>
#include <sstream>

#include "java_lang.h"

using namespace java::lang;

struct __Test;
struct __Test_VT;

typedef __Test* Test;

struct __Test {
  __Test_VT* __vptr;

  null


  static Class __class();

  static __Test_VT __vtable;
};

struct __Test_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Test);
  String (*toString)(Object);

  __Test_VT()
  : __isa(__Test::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Test))&__Object::getClass),
  toString(&__Object::toString) {}
};

int main(void);

