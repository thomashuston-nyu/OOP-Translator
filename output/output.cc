#include "output.h"

void __Sample::test(Sample __this) {
  int x;
  int y;
  int z;
  boolean b;
  String s;
  if (x == y) {
    z = x + y;
  } else {
    if (x + 1 == y) {
      z = x * y;
    } else {
      s = __rt::literal("asdf");
    }
  }
  while (z > x) {
    z--;
  }
  if (b) {
    z = x / y;
  }
  if (b == true) {
    z = 1;
  }
  if (x >= 5) {
    x = 6;
  }
  if (x != 6) {
    x = 7;
  }
  while (b) {
    b = false;
  }
}

__Sample_VT __Sample::__vtable;
