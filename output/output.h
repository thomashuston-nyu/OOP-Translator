#pragma once

#include <iostream>
#include <sstream>

#include "java_lang.h"

using namespace java::lang;

struct __Super;
struct __Super_VT;

typedef __Super* Super;

struct __Super {
  __Super_VT* __vptr;
  int32_t x;

  __Super(int32_t x1);

  static int32_t getX(Super);
  static void setX(Super, int32_t);
  static void printX(Super);
  static String toString(Super);

  static Class __class();

  static __Super_VT __vtable;
};

struct __Super_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Super);
  String (*toString)(Super);
  void (*printX)(Super);
  void (*setX)(Super, int32_t);
  int32_t (*getX)(Super);

  __Super_VT()
  : __isa(__Super::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Super))&__Object::getClass),
  toString(&__Super::toString),
  printX(&__Super::printX),
  setX(&__Super::setX),
  getX(&__Super::getX) {}
};



struct __Sub;
struct __Sub_VT;

typedef __Sub* Sub;

struct __Sub {
  __Sub_VT* __vptr;
  int32_t x;

  __Sub(int32_t x1);

  static int32_t getX(Sub);
  static void setX(Sub, int32_t);
  static String toString(Sub);

  static Class __class();

  static __Sub_VT __vtable;
};

struct __Sub_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Sub);
  String (*toString)(Sub);
  void (*printX)(Sub);
  void (*setX)(Sub, int32_t);
  int32_t (*getX)(Sub);

  __Sub_VT()
  : __isa(__Sub::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Sub))&__Object::getClass),
  toString(&__Sub::toString),
  printX((void(*)(Sub))&__Super::printX),
  setX(&__Sub::setX),
  getX(&__Sub::getX) {}
};



struct __Sample;
struct __Sample_VT;

typedef __Sample* Sample;

struct __Sample {
  __Sample_VT* __vptr;

  static Class __class();

  static __Sample_VT __vtable;
};

struct __Sample_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Sample);
  String (*toString)(Object);

  __Sample_VT()
  : __isa(__Sample::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Sample))&__Object::getClass),
  toString(&__Object::toString) {}
};

int main(void);

