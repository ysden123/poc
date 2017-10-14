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

}
