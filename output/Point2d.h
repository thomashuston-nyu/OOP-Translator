#include <iostream>
#include <sstream>

#include "java/java_lang.h"

using namespace java::lang;

struct __Point2d;
struct __Point2d_VT;

typedef __Point2d* Point2d;

struct __Point2d {
  __Point2d_VT* __vptr;
  double x, y;
  
  __Point2d(double px, double py);
  
  static String toString(Point2d);
  static void setX(Point2d, double);
  static double getX(Point2d);
  static void setY(Point2d, double);
  static double getY(Point2d);
  static void setXY(Point2d, double, double);
  static String toStringForXY(Point2d);
  
  static Class __class();
  
  static __Point2d_VT __vtable;
};

struct __Point2d_VT {
  Class __isa;
  int32_t (*hashCode)(Object);
  bool (*equals)(Object, Object);
  Class (*getClass)(Point2d);
  String (*toString)(Point2d);
  void (*setX)(Point2d, double);
  double (*getX)(Point2d);
  void (*setY)(Point2d, double);
  double (*getY)(Point2d);
  void (*setXY)(Point2d, double, double);
  String (*toStringForXY)(Point2d);
  
  __Point2d_VT()
  : __isa(__Point2d::__class()),
  hashCode(&__Object::hashCode),
  equals(&__Object::equals),
  getClass((Class(*)(Point2d))&__Object::getClass),
  toString(&__Point2d::toString),
  setX(&__Point2d::setX),
  getX(&__Point2d::getX),
  setY(&__Point2d::setY),
  getY(&__Point2d::getY),
  setXY(&__Point2d::setXY),
  toStringForXY(&__Point2d::toStringForXY) {
    
  }
  
};