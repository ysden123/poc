/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.stream.orders

import java.sql.Timestamp

/**
  * Order container
  *
  * @author Yuriy Stul
  */
case class Order(time: Timestamp, orderId: Long, clientId: Long, symbol: String, amount: Int, price: Double, buy: Boolean)
