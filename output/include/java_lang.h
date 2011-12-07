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

#pragma once

#include <stdint.h>
#include <stdio.h>
#include <string>
#include <iostream>
#include <cstring>
#include <math.h>

#include "ptr.h"

// ==========================================================================

// To avoid the "static initialization order fiasco", we use functions
// instead of fields/variables for all pointer values that are statically
// initialized.

// See http://www.parashift.com/c++-faq-lite/ctors.html#faq-10.14.

// ==========================================================================

namespace java {
  namespace lang {

    // Forward declarations of data layout and vtables.
    struct __Object;
    struct __Object_VT;

    struct __String;
    struct __String_VT;

    struct __Class;
    struct __Class_VT;

    // Definition of type names that are equivalent to Java semantics,
    // i.e., an instance is a smart pointer to the object's data layout.
    typedef __rt::Ptr<__Object> Object;
    typedef __rt::Ptr<__Class> Class;
    typedef __rt::Ptr<__String> String;
  }
}

// ==========================================================================

namespace __rt {

  // The function returning the canonical null value.
  java::lang::Object null();

}

// ==========================================================================

namespace java {
  namespace lang {

    // The data layout for java.lang.Object.
    struct __Object {
      __Object_VT* __vptr;

      // The constructor.
      __Object();
      static Object Object$void(Object = __rt::null());

      // The destructor.
      static void __delete(__Object*);

      // The methods implemented by java.lang.Object.
      static int32_t hashCode$void(Object);
      static bool equals$Object(Object, Object);
      static Class getClass$void(Object);
      static String toString$void(Object);

      // The function returning the class object representing
      // java.lang.Object.
      static Class __class();

      // The vtable for java.lang.Object.
      static __Object_VT __vtable;
    };

    // The vtable layout for java.lang.Object.
    struct __Object_VT {
      Class __isa;
      void (*__delete)(__Object*);
      int32_t (*hashCode$void)(Object);
      bool (*equals$Object)(Object, Object);
      Class (*getClass$void)(Object);
      String (*toString$void)(Object);

      __Object_VT()
      : __isa(__Object::__class()),
        __delete(&__Object::__delete),
        hashCode$void(&__Object::hashCode$void),
        equals$Object(&__Object::equals$Object),
        getClass$void(&__Object::getClass$void),
        toString$void(&__Object::toString$void) {
      }
    };

    // ======================================================================

    // The data layout for java.lang.String.
    struct __String {
      __String_VT* __vptr;
      std::string data;

      // The constructor;
      __String(std::string data);

      // The destructor.
      static void __delete(__String*);
      
      // The methods implemented by java.lang.String.
      static int32_t hashCode$void(String);
      static bool equals$Object(String, Object);
      static String toString$void(String);
      static int32_t length$void(String);
      static char charAt$int32_t(String, int32_t);

      // The function returning the class object representing
      // java.lang.String.
      static Class __class();

      // The vtable for java.lang.String.
      static __String_VT __vtable;
    };

    std::ostream& operator<<(std::ostream& out, String);

    // The vtable layout for java.lang.String.
    struct __String_VT {
      Class __isa;
      void (*__delete)(__String*);
      int32_t (*hashCode$void)(String);
      bool (*equals$Object)(String, Object);
      Class (*getClass$void)(String);
      String (*toString$void)(String);
      int32_t (*length$void)(String);
      char (*charAt$int32_t)(String, int32_t);
      
      __String_VT()
      : __isa(__String::__class()),
        __delete(__String::__delete),
        hashCode$void(&__String::hashCode$void),
        equals$Object(&__String::equals$Object),
        getClass$void((Class(*)(String))&__Object::getClass$void),
        toString$void(&__String::toString$void),
        length$void(&__String::length$void),
        charAt$int32_t(&__String::charAt$int32_t) {
      }
    };

    // ======================================================================

    // The data layout for java.lang.Class.
    struct __Class {
      __Class_VT* __vptr;
      String name;
      Class parent;
      Class component;
      bool primitive;

      // The constructor.
      __Class(String name,
              Class parent,
              Class component = __rt::null(),
              bool primitive = false);

      // The destructor.
      static void __delete(__Class*);

      // The instance methods of java.lang.Class.
      static String toString$void(Class);
      static String getName$void(Class);
      static Class getSuperclass$void(Class);
      static bool isPrimitive$void(Class);
      static bool isArray$void(Class);
      static Class getComponentType$void(Class);
      static bool isInstance$Object(Class, Object);

      // The function returning the class object representing
      // java.lang.Class.
      static Class __class();

      // The vtable for java.lang.Class.
      static __Class_VT __vtable;
    };

    // The vtable layout for java.lang.Class.
    struct __Class_VT {
      Class __isa;
      void (*__delete)(__Class*);
      int32_t (*hashCode$void)(Class);
      bool (*equals$Object)(Class, Object);
      Class (*getClass$void)(Class);
      String (*toString$void)(Class);
      String (*getName$void)(Class);
      Class (*getSuperclass$void)(Class);
      bool (*isPrimitive$void)(Class);
      bool (*isArray$void)(Class);
      Class (*getComponentType$void)(Class);
      bool (*isInstance$Object)(Class, Object);

      __Class_VT()
      : __isa(__Class::__class()),
        __delete(&__Class::__delete),
        hashCode$void((int32_t(*)(Class))&__Object::hashCode$void),
        equals$Object((bool(*)(Class,Object))&__Object::equals$Object),
        getClass$void((Class(*)(Class))&__Object::getClass$void),
        toString$void(&__Class::toString$void),
        getName$void(&__Class::getName$void),
        getSuperclass$void(&__Class::getSuperclass$void),
        isPrimitive$void(&__Class::isPrimitive$void),
        isArray$void(&__Class::isArray$void),
        getComponentType$void(&__Class::getComponentType$void),
        isInstance$Object(&__Class::isInstance$Object) {
      }
    };

    // ======================================================================

	struct __Byte {
		
		static Class TYPE();
		
	};
	
	struct __Short {
		
		static Class TYPE();
		
	};

    // The completey incomplete data layout for java.lang.Integer.
    struct __Integer {

      // The class instance representing the primitive type int.
      static Class TYPE();

    };

	struct __Long {
		
		static Class TYPE();
		
	};
	
	struct __Float {
		
		static Class TYPE();
		
	};
	
	struct __Double {
		
		static Class TYPE();
		
	};
	
	struct __Character {
		
		static Class TYPE();
		
	};
	
	struct __Boolean {
		
		static Class TYPE();
		
	};

    // ======================================================================

    // For simplicity, we use C++ inheritance for exceptions and throw
    // them by value.  In other words, the translator does not support
    // user-defined exceptions and simply relies on a few built-in
    // classes.
    class Throwable {
    };

    class Exception : public Throwable {
    };

    class RuntimeException : public Exception {
    };

    class NullPointerException : public RuntimeException {
    };

    class NegativeArraySizeException : public RuntimeException {
    };

    class ArrayStoreException : public RuntimeException {
    };

    class ClassCastException : public RuntimeException {
    };

    class IndexOutOfBoundsException : public RuntimeException {
    };

    class ArrayIndexOutOfBoundsException : public IndexOutOfBoundsException {
    };
    
  }
}

// ==========================================================================

namespace __rt {

  // Forward declarations of data layout and vtable.
  template <typename T>
  struct Array;

  template <typename T>
  struct Array_VT;

  // The data layout for arrays.
  template <typename T>
  struct Array {
    Array_VT<T>* __vptr;
    const int32_t length;
    T* __data;

    // The constructor (defined inline).
    Array(const int32_t length)
    : __vptr(&__vtable), length(length), __data(new T[length]) {
      // Only zero out __data for arrays of primitive types!
    }

    // The destructor.
    static void __delete(Array* __this) {
      delete[] __this->__data;
      delete __this;
    }

    // Array access.
    T& operator[](int32_t idx) {
      if (0 > idx || idx >= length) {
        throw java::lang::ArrayIndexOutOfBoundsException();
      }
      return __data[idx];
    }

    const T& operator[](int32_t idx) const {
      if (0 > idx || idx >= length) {
        throw java::lang::ArrayIndexOutOfBoundsException();
      }
      return __data[idx];
    }

    // The function returning the class object representing the array.
    static java::lang::Class __class();

    // The vtable for the array.
    static Array_VT<T> __vtable;
  };

  // The vtable for arrays.
  template <typename T>
  struct Array_VT {
    typedef Ptr<Array<T> > Reference;

    java::lang::Class __isa;
    void (*__delete)(Array<T>*);
    int32_t (*hashCode$void)(Reference);
    bool (*equals$Object)(Reference, java::lang::Object);
    java::lang::Class (*getClass$void)(Reference);
    java::lang::String (*toString$void)(Reference);
    
    Array_VT()
    : __isa(Array<T>::__class()),
      __delete(&Array<T>::__delete),
      hashCode$void((int32_t(*)(Reference))
               &java::lang::__Object::hashCode$void),
      equals$Object((bool(*)(Reference,java::lang::Object))
             &java::lang::__Object::equals$Object),
      getClass$void((java::lang::Class(*)(Reference))
               &java::lang::__Object::getClass$void),
      toString$void((java::lang::String(*)(Reference))
               &java::lang::__Object::toString$void) {
    }
  };

  // The vtable for arrays.  Note that this definition uses the default
  // no-arg constructor.
  template <typename T>
  Array_VT<T> Array<T>::__vtable;

  // But where is the definition of __class()???

  // ========================================================================

  // Function for converting a C string lieral to a translated
  // Java string.
  inline java::lang::String literal(const char * s) {
    // C++ implicitly converts the C string to a std::string.
    return new java::lang::__String(s);
  }

  // ========================================================================

  // Template function to check against null values.
  template <typename T>
  void checkNotNull(T o) {
    if (null() == o) throw java::lang::NullPointerException();
  }

  // Template function to check array stores.
  template <typename T, typename U>
  void checkStore(Ptr<Array<T> > array, U object) {
    if (null() != object) {
      java::lang::Class t1 = array->__vptr->getClass$void(array);
      java::lang::Class t2 = t1->__vptr->getComponentType$void(t1);

      if (! t2->__vptr->isInstance$Object(t2, object)) {
        throw java::lang::ArrayStoreException();
      }
    }
  }

  // ========================================================================

  // Template function for translated Java casts.
  template <typename T, typename U>
  T java_cast(U object) {
    java::lang::Class k = T::value_t::__class();

    if (! k->__vptr->isInstance$Object(k, object)) {
      throw java::lang::ClassCastException();
    }

    return T(object);
  }

}
