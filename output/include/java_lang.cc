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

    Object __Object::$__Object$void() {
      Object $con$ = new __Object();
      return $con$;
    }

    // The destructor.
    void __Object::__delete(__Object* __this) {
      delete __this;
    }

    // java.lang.Object.hashCode()
    int32_t __Object::hashCode$void(Object __this) {
      return (int32_t)(intptr_t)__this.raw();
    }

    // java.lang.Object.equals(Object)
    bool __Object::equals$Object(Object __this, Object other) {
      return __this == other;
    }

    // java.lang.Object.getClass()
    Class __Object::getClass$void(Object __this) {
      return __this->__vptr->__isa;
    }

    // java.lang.Object.toString()
    String __Object::toString$void(Object __this) {
      // Class k = this.getClass();
      Class k = __this->__vptr->getClass$void(__this);

      std::ostringstream sout;
      sout << k->__vptr->getName$void(k)->data
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
      __super = __Object::$__Object$void();
    }
	
    // The destructor.
    void __String::__delete(__String* __this) {
      delete __this;
    }

    // java.lang.String.hashCode()
    int32_t __String::hashCode$void(String __this) {
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
    String __String::toString$void(String __this) {
      return __this;
    }

    // java.lang.String.length()
    int32_t __String::length$void(String __this) {
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
      __super = __Object::$__Object$void();
    }

    // The destructor.
    void __Class::__delete(__Class* __this) {
      delete __this;
    }

    // java.lang.Class.toString()
    String __Class::toString$void(Class __this) {
      if (__this->primitive) {
        return __this->name;
      } else {
        return new __String("class " + __this->name->data);
      }
    }

    // java.lang.Class.getName()
    String __Class::getName$void(Class __this) {
      return __this->name;
    }

    // java.lang.Class.getSuperclass()
    Class __Class::getSuperclass$void(Class __this) {
      return __this->parent;
    }

    // java.lang.Class.isPrimitive()
    bool __Class::isPrimitive$void(Class __this) {
      return __this->primitive;
    }

    // java.lang.Class.isArray()
    bool __Class::isArray$void(Class __this) {
      return __rt::null() != __this->component;
    }

    // java.lang.Class.getComponentType()
    Class __Class::getComponentType$void(Class __this) {
      return __this->component;
    }

    // java.lang.Class.isInstance(Object)
    bool __Class::isInstance$Object(Class __this, Object o) {
      Class k = o->__vptr->getClass$void(o);

      do {
        if (__this->__vptr->equals$Object(__this, k)) return true;

        k = k->__vptr->getSuperclass$void(k);
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

  // Template specialization for arrays of objects.
  template<>
  java::lang::Class Array<java::lang::Object>::__class() {
    static java::lang::Class k =
      new java::lang::__Class(literal("[Ljava.lang.Object;"),
                              java::lang::__Object::__class(),
                              java::lang::__Object::__class());
    return k;
  }


}
