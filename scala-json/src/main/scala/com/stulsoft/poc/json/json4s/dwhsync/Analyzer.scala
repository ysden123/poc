/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.json.json4s.dwhsync

import com.typesafe.scalalogging.StrictLogging
import org.json4s._
import org.json4s.jackson.Serialization.read

import java.io.FileInputStream

/**
 * @author Yuriy Stul
 */
object Analyzer extends App with StrictLogging {
  implicit val formats: DefaultFormats = DefaultFormats

  try {
    logger.info("Started")
    val input = new FileInputStream("c:\\Users\\yuriy\\Downloads\\DwhSyncManualAccountsSummary.json");
    val syncResult = read[SyncResult](input)
    input.close()
    logger.info("Had read")
    val totalManualAccounts = syncResult.manualAccounts.accounts.size
    val totalManualAccountLastRun = syncResult.manualAccountLastRun.accounts.size
    val totalManualAccountsUpdated = countUpdated(syncResult.manualAccounts.accounts)
    val totalManualAccountLastRunUpdated = countUpdated(syncResult.manualAccountLastRun.accounts)

    logger.info("The manual accounts: {} accounts from {} were updated",
      totalManualAccountsUpdated, totalManualAccounts)
    logger.info("The manualLastRun accounts: {} accounts from {} were updated",
      totalManualAccountLastRunUpdated, totalManualAccountLastRun)
  } catch {
    case exception: Exception =>
      logger.error(exception.getMessage, exception)
  }

  def countUpdated(syncStatuses: List[SyncStatus]): Int = {
    syncStatuses.count(syncStatus => !syncStatus.description.contains("is up to date"))
  }
}
