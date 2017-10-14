/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.typeinfo

import java.lang.reflect.{Field, Method}


/**
  * @author Yuriy Stul
  */
trait YSTypeInfo {

  /** Returns declared methods
    *
    * @return the declared methods
    */
  def declaredMethods: List[Method] = {
    val fields = declaredFields
    this.getClass.getDeclaredMethods.toList
      .filter(m => {
        !fields.exists(f => f.getName == m.getName) && (m.getName match {
          case "declaredFields" | "declaredMethods" => false
          case _ => true
        })
      })
  }

  /**
    * Returns declared fields
    *
    * @return the declared fields
    */
  def declaredFields: List[Field] = {

    this.getClass.getDeclaredFields.toList.filter(!_.isSynthetic)
  }
}
