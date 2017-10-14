/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.typeinfo

import java.lang.reflect.Field


/**
  * @author Yuriy Stul
  */
trait YSTypeInfo {

  /**
    * Returns declared fields
    *
    * @return the declared fields
    */
  def declaredFields: List[Field] = {
    this.getClass.getDeclaredFields.toList.filter(!_.isSynthetic)
  }
}
