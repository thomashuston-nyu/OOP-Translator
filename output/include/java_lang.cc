/*
 * Object-Oriented Programming
 * Copyright (C) 2011 Robert Grimm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */

#include "java_lang.h"

#include <sstream>

namespace java {
  namespace lang {

    // java.lang.Object()
    __Object::__Object() : __vptr(&__vtable) {
    }

    // The destructor.
    void __Object::__delete(__Object* __this) {
      delete __this;
    }

    // java.lang.Object.hashCode()
    int32_t __Object::hashCode(Object __this) {
      return (int32_t)(intptr_t)__this.raw();
    }

    // java.lang.Object.equals(Object)
    bool __Object::equals$Object(Object __this, Object other) {
      return __this == other;
    }

    // java.lang.Object.getClass()
    Class __Object::getClass(Object __this) {
      return __this->__vptr->__isa;
    }

    // java.lang.Object.toString()
    String __Object::toString(Object __this) {
      // Class k = this.getClass();
      Class k = __this->__vptr->getClass(__this);

      std::ostringstream sout;
      sout << k->__vptr->getName(k)->data
           << '@' << std::hex << (uintptr_t)__this.raw();
      return new __String(sout.str());
    }

    // Internal accessor for java.lang.Object's class.
    Class __Object::__class() {
      static Class k =
        new __Class(__rt::literal("java.lang.Object"), __rt::null());
      return k;
    }

    // The vtable for java.lang.Object.  Note that this definition
    // invokes the default no-arg constructor for __Object_VT.
    __Object_VT __Object::__vtable;

    // =======================================================================

    // java.lang.String(<literal>)
    __String::__String(std::string data)
      : __vptr(&__vtable), 
        data(data) {
    }

    // The destructor.
    void __String::__delete(__String* __this) {
      delete __this;
    }

    // java.lang.String.hashCode()
    int32_t __String::hashCode(String __this) {
      int32_t hash = 0;

      // Use a C++ iterator to access string's characters.
      for (std::string::iterator itr = __this->data.begin();
           itr < __this->data.end();
           itr++) {
        hash = 31 * hash + *itr;
      }

      return hash;
    }

    // java.lang.String.equals()
    bool __String::equals$Object(String __this, Object o) {
      // Make sure object is a string:
      // if (! o instanceof String) return false;
      Class k = __String::__class();
      if (! k->__vptr->isInstance$Object(k, o)) return false;

      // Do the actual comparison.
      String other = o; // Implicit downcast.
      return __this->data.compare(other->data) == 0;
    }

    // java.lang.String.toString()
    String __String::toString(String __this) {
      return __this;
    }

    // java.lang.String.length()
    int32_t __String::length(String __this) {
      return __this->data.length();
    }

    // java.lang.String.charAt()
    char __String::charAt$int32_t(String __this, int32_t idx) {
      if (0 > idx || idx >= __this->data.length()) {
        throw IndexOutOfBoundsException();
      }

      // Use std::string::operator[] to get character without
      // duplicate range check.
      return __this->data[idx];
    }

    // Internal accessor for java.lang.String's class.
    Class __String::__class() {
      static Class k =
        new __Class(__rt::literal("java.lang.String"), __Object::__class());
      return k;
    }

    // The vtable for java.lang.String.  Note that this definition
    // invokes the default no-arg constructor for __String_VT.
    __String_VT __String::__vtable;

    std::ostream& operator<<(std::ostream& out, String s) {
      out << s->data;
      return out;
    }

    // =======================================================================

    // java.lang.Class(String, Class)
    __Class::__Class(String name, Class parent, Class component, bool primitive)
      : __vptr(&__vtable),
        name(name),
        parent(parent),
        component(component),
        primitive(primitive) {
    }

    // The destructor.
    void __Class::__delete(__Class* __this) {
      delete __this;
    }

    // java.lang.Class.toString()
    String __Class::toString(Class __this) {
      if (__this->primitive) {
        return __this->name;
      } else {
        return new __String("class " + __this->name->data);
      }
    }

    // java.lang.Class.getName()
    String __Class::getName(Class __this) {
      return __this->name;
    }

    // java.lang.Class.getSuperclass()
    Class __Class::getSuperclass(Class __this) {
      return __this->parent;
    }

    // java.lang.Class.isPrimitive()
    bool __Class::isPrimitive(Class __this) {
      return __this->primitive;
    }

    // java.lang.Class.isArray()
    bool __Class::isArray(Class __this) {
      return __rt::null() != __this->component;
    }

    // java.lang.Class.getComponentType()
    Class __Class::getComponentType(Class __this) {
      return __this->component;
    }

    // java.lang.Class.isInstance(Object)
    bool __Class::isInstance$Object(Class __this, Object o) {
      Class k = o->__vptr->getClass(o);

      do {
        if (__this->__vptr->equals$Object(__this, k)) return true;

        k = k->__vptr->getSuperclass(k);
      } while (__rt::null() != k);

      return false;
    }

    // Internal accessor for java.lang.Class' class.
    Class __Class::__class() {
      static Class k = 
        new __Class(__rt::literal("java.lang.Class"), __Object::__class());
      return k;
    }

    // The vtable for java.lang.Class.  Note that this definition
    // invokes the default no-arg constructor for __Class_VT.
    __Class_VT __Class::__vtable;

    // =======================================================================

	// java.lang.Byte.TYPE
	Class __Byte::TYPE() {
      static Class k =
        new __Class(__rt::literal("byte"), __rt::null(), __rt::null(), true);
      return k;
    }

	// java.lang.Short.TYPE
	Class __Short::TYPE() {
      static Class k =
        new __Class(__rt::literal("short"), __rt::null(), __rt::null(), true);
      return k;
    }

    // java.lang.Integer.TYPE
    Class __Integer::TYPE() {
      static Class k =
        new __Class(__rt::literal("int"), __rt::null(), __rt::null(), true);
      return k;
    }

	// java.lang.Long.TYPE
	Class __Long::TYPE() {
      static Class k =
        new __Class(__rt::literal("long"), __rt::null(), __rt::null(), true);
      return k;
    }

	// java.lang.Float.TYPE
	Class __Float::TYPE() {
      static Class k =
        new __Class(__rt::literal("float"), __rt::null(), __rt::null(), true);
      return k;
    }

	// java.lang.Double.TYPE
	Class __Double::TYPE() {
      static Class k =
        new __Class(__rt::literal("double"), __rt::null(), __rt::null(), true);
      return k;
    }

	// java.lang.Character.TYPE
	Class __Character::TYPE() {
      static Class k =
        new __Class(__rt::literal("char"), __rt::null(), __rt::null(), true);
      return k;
    }

	// java.lang.Boolean.TYPE
	Class __Boolean::TYPE() {
      static Class k =
        new __Class(__rt::literal("boolean"), __rt::null(), __rt::null(), true);
      return k;
    }

  }
}

// ===========================================================================

namespace __rt {

  // The function returning the canonical null value.
  java::lang::Object null() {
    static java::lang::Object value(0);
    return value;
  }

  // Template specialization for arrays of bytes.
  template<>
  Array<unsigned char>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new unsigned char[length]) {
    std::memset(__data, 0, length * sizeof(unsigned char));
  }

  template<>
  java::lang::Class Array<unsigned char>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[B"),
                              java::lang::__Object::__class(),
                              java::lang::__Byte::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<unsigned char> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[B"),
                              Array<unsigned char>::__class(),
                              java::lang::__Byte::TYPE());
    return k;
  }

  // Template specialization for arrays of shorts.
  template<>
  Array<int16_t>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new int16_t[length]) {
    std::memset(__data, 0, length * sizeof(int16_t));
  }

  template<>
  java::lang::Class Array<int16_t>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[S"),
                              java::lang::__Object::__class(),
                              java::lang::__Short::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<int16_t> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[S"),
                              Array<int16_t>::__class(),
                              java::lang::__Short::TYPE());
    return k;
  }

  // Template specialization for arrays of ints.
  template<>
  Array<int32_t>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new int32_t[length]) {
    std::memset(__data, 0, length * sizeof(int32_t));
  }

  template<>
  java::lang::Class Array<int32_t>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[I"),
                              java::lang::__Object::__class(),
                              java::lang::__Integer::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<int32_t> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[I"),
                              Array<int32_t>::__class(),
                              java::lang::__Integer::TYPE());
    return k;
  }

  // Template specialization for arrays of longs.
  template<>
  Array<int64_t>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new int64_t[length]) {
    std::memset(__data, 0, length * sizeof(int64_t));
  }

  template<>
  java::lang::Class Array<int64_t>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[J"),
                              java::lang::__Object::__class(),
                              java::lang::__Long::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<int64_t> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[J"),
                              Array<int64_t>::__class(),
                              java::lang::__Long::TYPE());
    return k;
  }

  // Template specialization for arrays of floats.
  template<>
  Array<float>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new float[length]) {
    std::memset(__data, 0, length * sizeof(float));
  }

  template<>
  java::lang::Class Array<float>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[F"),
                              java::lang::__Object::__class(),
                              java::lang::__Float::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<float> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[F"),
                              Array<float>::__class(),
                              java::lang::__Float::TYPE());
    return k;
  }

  // Template specialization for arrays of doubles.
  template<>
  Array<double>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new double[length]) {
    std::memset(__data, 0, length * sizeof(double));
  }

  template<>
  java::lang::Class Array<double>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[D"),
                              java::lang::__Object::__class(),
                              java::lang::__Double::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<double> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[D"),
                              Array<double>::__class(),
                              java::lang::__Double::TYPE());
    return k;
  }

  // Template specialization for arrays of chars.
  template<>
  Array<char>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new char[length]) {
    std::memset(__data, 0, length * sizeof(char));
  }

  template<>
  java::lang::Class Array<char>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[C"),
                              java::lang::__Object::__class(),
                              java::lang::__Character::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<char> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[C"),
                              Array<char>::__class(),
                              java::lang::__Character::TYPE());
    return k;
  }

  // Template specialization for arrays of booleans.
  template<>
  Array<bool>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new bool[length]) {
    std::memset(__data, 0, length * sizeof(bool));
  }

  template<>
  java::lang::Class Array<bool>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[Z"),
                              java::lang::__Object::__class(),
                              java::lang::__Boolean::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<bool> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[Z"),
                              Array<bool>::__class(),
                              java::lang::__Boolean::TYPE());
    return k;
  }

  // Template specialization for arrays of objects.
  template<>
  java::lang::Class Array<java::lang::Object>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[Ljava.lang.Object;"),
                              java::lang::__Object::__class(),
                              java::lang::__Object::__class());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<java::lang::Object> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[Ljava.lang.Object;"),
                              Array<java::lang::Object>::__class(),
                              java::lang::__Object::__class());
    return k;
  }

  // Template specialization for arrays of strings.
  template<>
  java::lang::Class Array<java::lang::String>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[Ljava.lang.String;"),
                              Array<java::lang::Object>::__class(),
                              java::lang::__String::__class());
    return k;
  }

  template<>
  java::lang::Class Array<Ptr<Array<java::lang::String> > >::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[[Ljava.lang.String;"),
                              Array<java::lang::String>::__class(),
                              java::lang::__String::__class());
    return k;
  }

}
