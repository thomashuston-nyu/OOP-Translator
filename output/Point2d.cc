#include "Point2d.h"

__Point2d::__Point2d(double px, double py)
: __vptr(&__vtable),
x(px),
y(py) {
}

String __Point2d::toString(Point2d __this) {
  std::ostringstream sout;
  sout << __this->toStringForXY(__this)->data << ")";
  return new __String(sout.str());
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
  __this->setX(__this, px);
  __this->setY(__this, py);
}

String __Point2d::toStringForXY(Point2d __this) {
  std::ostringstream sout;
  sout << "(" << __this->x << ", "  << __this->y;
  return new __String(sout.str());
}

Class __Point2d::__class() {
  static Class k =
  new __Class(__rt::literal("Point2d"), __Object::__class());
  return k;
}

__Point2d_VT __Point2d::__vtable;

int main() {
  Point2d p = new __Point2d(5.0,7.0);
  std::cout << p->__vptr->toString(p)->data << std::endl;
  return 0;
}