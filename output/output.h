#pragma once

#include <iostream>
#include <sstream>

#include "../java/java_lang.h"

using namespace java::lang;

struct __Sample;
struct __Sample_VT;

typedef __Sample* Sample;

struct __Sample {
  __Sample_VT* __vptr;

  null

  static void test(Sample);

  static Class __class();

  static __Sample_VT __vtable;
};

struct __Sample_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Object);
  String (*toString)(Object);
  void (*test)(Sample);

  __Sample_VT()
  : __isa(__Sample::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  ((Class(*)(Sample))&__Object::getClass),
  toString(&__Object::toString),
  test(&__Sample::test) {}
};
