#pragma once

#include <iostream>
#include <sstream>

#include "java_lang.h"

using namespace java::lang;

struct __Sample;
struct __Sample_VT;

typedef __Sample* Sample;

struct __Sample {
  __Sample_VT* __vptr;
  int32_t x;
  int32_t y;

  __Sample(int32_t x1, int32_t y1);

  static int32_t getX(Sample);
  static String toString(Sample);

  static Class __class();

  static __Sample_VT __vtable;
};

struct __Sample_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Sample);
  String (*toString)(Sample);
  int32_t (*getX)(Sample);

  __Sample_VT()
  : __isa(__Sample::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Sample))&__Object::getClass),
  toString(&__Sample::toString),
  getX(&__Sample::getX) {}
};

int main(void);

