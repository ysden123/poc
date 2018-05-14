package com.stulsoft

import slick.lifted.TableQuery

/**
  * @author Yuriy Stul
  */
package object pslick {
  val suppliers = TableQuery[Suppliers]
  val coffees = TableQuery[Coffees]
}
