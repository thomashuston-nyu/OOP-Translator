#include "output.h"

__ShallowClass::__ShallowClass()
: __vptr(&__vtable) {
}

int32_t __ShallowClass::test(ShallowClass __this) {
  return 1;
}

Class __ShallowClass::__class() {
  static Class k = new __Class(__rt::literal("ShallowClass"), __Object::__class());
  return k;
}

__ShallowClass_VT __ShallowClass::__vtable;

__AClass::__AClass()
: __vptr(&__vtable) {
}

int32_t __AClass::test(AClass __this) {
  return 1;
}

Class __AClass::__class() {
  static Class k = new __Class(__rt::literal("AClass"), __Object::__class());
  return k;
}

__AClass_VT __AClass::__vtable;

__BClass::__BClass()
: __vptr(&__vtable) {
}

Class __BClass::__class() {
  static Class k = new __Class(__rt::literal("BClass"), __Object::__class());
  return k;
}

__BClass_VT __BClass::__vtable;

__DeepClass::__DeepClass()
: __vptr(&__vtable) {
}

int32_t __DeepClass::test(DeepClass __this) {
  return 2;
}

Class __DeepClass::__class() {
  static Class k = new __Class(__rt::literal("DeepClass"), __Object::__class());
  return k;
}

__DeepClass_VT __DeepClass::__vtable;

__Point2d::__Point2d(double px, double py)
: __vptr(&__vtable) {
  x = px;
  y = py;
}

void __Point2d::setX(Point2d __this, double px) {
  __this->x = px;
}

double __Point2d::getX(Point2d __this) {
  return __this->x;
}

void __Point2d::setY(Point2d __this, double py) {
  __this->y = py;
}

double __Point2d::getY(Point2d __this) {
  return __this->y;
}

void __Point2d::setXY(Point2d __this, double px, double py) {
  __this->__vptr->setX(__this, px);
  __this->__vptr->setY(__this, py);
}

String __Point2d::toStringForXY(Point2d __this) {
  String str = __rt::literal("()");
  return str;
}

String __Point2d::toString(Point2d __this) {
  String str = __rt::literal("()");
  return str;
}

Class __Point2d::__class() {
  static Class k = new __Class(__rt::literal("Point2d"), __Object::__class());
  return k;
}

__Point2d_VT __Point2d::__vtable;

int main(void) {
  Point2d p = new __Point2d(5.0, 7.0);
  println(p->__vptr->toString(p)->data);
  bool x = false;
  println(bool_to_string(x));
}

