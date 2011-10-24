#include "output.h"

__Sample::__Sample(int32_t x1, int32_t y1)
: __vptr(&__vtable) {
  x = x1;
  y = y1;
}

int32_t __Sample::getX(Sample __this) {
  return __this->x;
}

String __Sample::toString(Sample __this) {
  return __rt::literal("ok");
}

Class __Sample::__class() {
  static Class k = new __Class(__rt::literal("Sample"), __Object::__class());
  return k;
}

__Sample_VT __Sample::__vtable;

int main(void) {
  Sample sample = new __Sample(3, 8);
  int32_t a = sample->__vptr->getX(sample);
  if (a < 2) {
    println(__rt::literal("a < 2")->data);
  } else {
    while (a > 2) {
      a--;
    }
    println(int_to_string(a));
  }
}

