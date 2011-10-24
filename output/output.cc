#include "output.h"

__ShallowClass::__ShallowClass()
: __vptr(&__vtable) {
}

int __ShallowClass::test(ShallowClass __this) {
  return;
}

__ShallowClass_VT __ShallowClass::__vtable;

__AClass::__AClass()
: __vptr(&__vtable) {
}

int __AClass::test(AClass __this) {
  return;
}

__AClass_VT __AClass::__vtable;

__BClass::__BClass()
: __vptr(&__vtable) {
}

__BClass_VT __BClass::__vtable;

__DeepClass::__DeepClass()
: __vptr(&__vtable) {
}

int __DeepClass::test(DeepClass __this) {
  return;
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
  String str = __rt::literal(__rt::literal("(") + __this->x + __rt::literal(", ") + __this->y);
  return str;
}

String __Point2d::toString(Point2d __this) {
  String str = __rt::literal(__this->__vptr->toStringForXY(__this) + __rt::literal(")"));
  return str;
}

__Point2d_VT __Point2d::__vtable;
