#include "output.h"

Class __Test::__class() {
  static Class k = new __Class(__rt::literal("Test"), __Object::__class());
  return k;
}

__Test_VT __Test::__vtable;

int main(void) {
  int32_t x = 0;
  for (int32_t i = 0; i < 10; i++) {
    x++;
    println(int_to_string(x));
  }
}

