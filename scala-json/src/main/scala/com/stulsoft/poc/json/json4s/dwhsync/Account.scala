/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.json.json4s.dwhsync

/**
 * @author Yuriy Stul
 */
case class Account(accID:Int,
                   accSapID:String,
                   externalAccID:String,
                   accName:String,
                   affiliateProgramID:Int,
                   affiliateProgramName:String,
                   legalCompanyID:Int,
                   legalCompanyName:String,
                   userName:String,
                   visitSite:String,
                   dealType:String,
                   recordType:String,
                   reportType:String,
                   mode:String)
