#include "output.h"

namespace test {
  __Point2d::__Point2d(double px, double py)
  : __vptr(&__vtable) {
    x = px;
    y = py;
  }

  void __Point2d::setX(Point2d __this, double px) {
    x = px;
  }

  double __Point2d::getX(Point2d __this) {
    return x;
  }

  void __Point2d::setY(Point2d __this, double py) {
    y = py;
  }

  double __Point2d::getY(Point2d __this) {
    return y;
  }

  void __Point2d::setXY(Point2d __this, double px, double py) {
    __vptr->setX(__this, px);
    __vptr->setY(__this, py);
  }

  String __Point2d::toStringForXY(Point2d __this) {
    String str = ;
    return str;
  }

  String __Point2d::toString(Point2d __this) {
    String str = __vptr->toStringForXY(__this) + ")";
    return str;
  }

  __Point2d_VT __Point2d::__vtable;

}