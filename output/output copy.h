#pragma once

#include <iostream>
#include <sstream>

#include "java_lang.h"

using namespace java::lang;


struct __ShallowClass;
struct __ShallowClass_VT;

typedef __ShallowClass* ShallowClass;

struct __ShallowClass {
  __ShallowClass_VT* __vptr;

  __ShallowClass();

  static int test(ShallowClass);

  static Class __class();

  static __ShallowClass_VT __vtable;
};

struct __ShallowClass_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(ShallowClass);
  String (*toString)(Object);
  int (*test)(ShallowClass);

  __ShallowClass_VT()
  : __isa(__ShallowClass::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(ShallowClass))&__Object::getClass),
  toString(&__Object::toString),
  test(&__ShallowClass::test) {}
};


struct __DeepClass;
struct __DeepClass_VT;

typedef __DeepClass* DeepClass;

struct __DeepClass {
  __DeepClass_VT* __vptr;
  
  __DeepClass();
  
  static int test(DeepClass);
  
  static Class __class();
  
  static __DeepClass_VT __vtable;
};

struct __DeepClass_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(DeepClass);
  String (*toString)(Object);
  int32_t (*test)(DeepClass);
  
  __DeepClass_VT()
  : __isa(__DeepClass::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(DeepClass))&__Object::getClass),
  toString(&__Object::toString),
  test(&__DeepClass::test) {}
};


struct __AClass;
struct __AClass_VT;

typedef __AClass* AClass;

struct __AClass {
  __AClass_VT* __vptr;
  
  __AClass();
  
  static int test(AClass);
  
  static Class __class();
  
  static __AClass_VT __vtable;
};

struct __AClass_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(AClass);
  String (*toString)(Object);
  int32_t (*test)(AClass);
  
  __AClass_VT()
  : __isa(__AClass::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(AClass))&__Object::getClass),
  toString(&__Object::toString),
  test(&__AClass::test) {}
};


struct __BClass;
struct __BClass_VT;

typedef __BClass* BClass;

struct __BClass {
  __BClass_VT* __vptr;
  
  __BClass();
  
  
  static Class __class();
  
  static __BClass_VT __vtable;
};

struct __BClass_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(BClass);
  String (*toString)(Object);
  int32_t (*test)(BClass);
  
  __BClass_VT()
  : __isa(__BClass::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(BClass))&__Object::getClass),
  toString(&__Object::toString),
  test((int32_t(*)(BClass))&__AClass::test) {}
};


struct __Point2d;
struct __Point2d_VT;

typedef __Point2d* Point2d;

struct __Point2d {
  __Point2d_VT* __vptr;
  double x;
  double y;

  __Point2d(double px, double py);

  static void setX(Point2d, double);
  static double getX(Point2d);
  static void setY(Point2d, double);
  static double getY(Point2d);
  static void setXY(Point2d, double, double);
  static String toStringForXY(Point2d);
  static String toString(Point2d);

  static Class __class();

  static __Point2d_VT __vtable;
};

struct __Point2d_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Point2d);
  String (*toString)(Point2d);
  String (*toStringForXY)(Point2d);
  double (*getY)(Point2d);
  void (*setX)(Point2d, double);
  void (*setXY)(Point2d, double, double);
  void (*setY)(Point2d, double);
  double (*getX)(Point2d);

  __Point2d_VT()
  : __isa(__Point2d::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Point2d))&__Object::getClass),
  toString(&__Point2d::toString),
  toStringForXY(&__Point2d::toStringForXY),
  getY(&__Point2d::getY),
  setX(&__Point2d::setX),
  setXY(&__Point2d::setXY),
  setY(&__Point2d::setY),
  getX(&__Point2d::getX) {}
};

