#include "output.h"

__Super::__Super(int32_t x1)
: __vptr(&__vtable) {
  x = x1;
}

int32_t __Super::getX(Super __this) {
  return __this->x;
}

void __Super::setX(Super __this, int32_t x1) {
  __this->x = x1;
}

void __Super::printX(Super __this) {
  println(int_to_string(__this->x));
}

String __Super::toString(Super __this) {
  return __rt::literal("This is Super.");
}

Class __Super::__class() {
  static Class k = new __Class(__rt::literal("Super"), __Object::__class());
  return k;
}

__Super_VT __Super::__vtable;

__Sub::__Sub(int32_t x1)
: __vptr(&__vtable) {
  x = x1;
}

int32_t __Sub::getX(Sub __this) {
  return __this->x;
}

void __Sub::setX(Sub __this, int32_t x1) {
  __this->x = x1 + 10;
}

String __Sub::toString(Sub __this) {
  return __rt::literal("This is Sub.");
}

Class __Sub::__class() {
  static Class k = new __Class(__rt::literal("Sub"), __Object::__class());
  return k;
}

__Sub_VT __Sub::__vtable;

Class __Sample::__class() {
  static Class k = new __Class(__rt::literal("Sample"), __Object::__class());
  return k;
}

__Sample_VT __Sample::__vtable;

int main(void) {
  Super sup = new __Super(1);
  Sub sub = new __Sub(2);
  sup->__vptr->printX(sup);
  sup->__vptr->setX(sup, 5);
  int32_t v = sup->__vptr->getX(sup);
  println(int_to_string(v));
  println(sup->__vptr->toString(sup)->data);
  println(sub->__vptr->toString(sub)->data);
  sub->__vptr->setX(sub, 10);
  int32_t w = sub->__vptr->getX(sub);
  println(int_to_string(w));
  if (v < 5) {
    println(__rt::literal("v < 5")->data);
  } else {
    if (v == 5) {
      println(__rt::literal("v = 5")->data);
    } else {
      println(__rt::literal("v > 5")->data);
    }
  }
  while (v < 10) {
    v++;
  }
  print(__rt::literal("v = ")->data);
  println(int_to_string(v));
  for (int32_t i = 0; i < 10; i++) {
    print(int_to_string(i));
  }
  println();
}

