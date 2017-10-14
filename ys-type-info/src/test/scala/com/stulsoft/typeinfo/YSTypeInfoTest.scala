/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.typeinfo

import org.scalatest.{FlatSpec, Matchers}

/** Unit tests for YSTypeInfo class
  *
  * @author Yuriy Stul
  */
class YSTypeInfoTest extends FlatSpec with Matchers {

  behavior of "YSTypeInfoTest"

  "declaredFields" should "return declared fields" in {
    class C1(val fieldOne: String, val fieldTwo: Int) extends YSTypeInfo
    val fields = new C1("test1", 2).declaredFields

    fields.length shouldBe 2

    fields.exists(f => f.getName == "fieldOne") shouldBe true
    fields.exists(f => f.getName == "fieldTwo") shouldBe true
  }

  it should "work with case class" in {
    case class C1(fieldOne: String, fieldTwo: Int) extends YSTypeInfo
    val fields = C1("test1", 2).declaredFields

    fields.length shouldBe 2

    fields.exists(f => f.getName == "fieldOne") shouldBe true
    fields.exists(f => f.getName == "fieldTwo") shouldBe true
  }

  it should "work with inheritance" in {
    class C1(val fieldOne: String, val fieldTwo: Int)
    class C2(fieldOne: String, fieldTwo: Int, val fieldThree: Double)
      extends C1(fieldOne = fieldOne, fieldTwo = fieldTwo)
        with YSTypeInfo

    val fields = new C2("one", 2, 3.0).declaredFields

    fields.length shouldBe 1

    fields.exists(f => f.getName == "fieldThree") shouldBe true
  }

  "declaredMethods" should "return declared methods" in {
    class C1(val fieldOne: String, val fieldTwo: Int) extends YSTypeInfo {
      def multiple(multiplier: Int): Int = {
        fieldTwo * multiplier
      }
    }

    val methods = new C1("field one", 123).declaredMethods
    methods.length shouldBe 1
    methods.exists(f => f.getName == "multiple") shouldBe true
  }

  it should "work with inheritance 1" in {
    class C1(val fieldOne: String)
    class C2(fieldOne: String, val fieldTwo: Int) extends C1(fieldOne) with YSTypeInfo {
      def multiple(multiplier: Int): Int = {
        fieldTwo * multiplier
      }
    }
    val methods = new C2("field one", 123).declaredMethods
    methods.length shouldBe 1
    methods.exists(f => f.getName == "multiple") shouldBe true
  }

  it should "work with inheritance 2" in {
    class C1(val fieldOne: String) {
      def lengthCalculator: Int = fieldOne.length
    }
    class C2(fieldOne: String, val fieldTwo: Int) extends C1(fieldOne) with YSTypeInfo {
      def multiple(multiplier: Int): Int = {
        fieldTwo * multiplier
      }
    }
    val methods = new C2("field one", 123).declaredMethods
    methods.length shouldBe 1
    methods.exists(f => f.getName == "multiple") shouldBe true
  }

}
