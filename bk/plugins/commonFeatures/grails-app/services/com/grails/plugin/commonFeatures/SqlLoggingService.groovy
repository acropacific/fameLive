package com.grails.plugin.commonFeatures

import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.hibernate.stat.Statistics

class SqlLoggingService {
    def sessionFactory

    public def execute(Closure closure) {
        Logger sqlLogger = Logger.getLogger("org.hibernate.SQL");
        Level currentLevel = sqlLogger.level
        sqlLogger.setLevel(Level.TRACE)
        def result = closure.call()
        sqlLogger.setLevel(currentLevel)
        result
    }

    public def executeWithStatistics(Closure closure) {
        Statistics stats = sessionFactory.statistics;
        if (!stats.statisticsEnabled) {stats.setStatisticsEnabled(true)}

        def result = closure.call()

        double queryCacheHitCount = stats.getQueryCacheHitCount();
        double queryCacheMissCount = stats.getQueryCacheMissCount();
        double queryCacheHitRatio = (queryCacheHitCount / ((queryCacheHitCount + queryCacheMissCount) ?: 1))
        log.info """
######################## Hibernate Stats ##############################################
Transaction Count:${stats.transactionCount}
Flush Count:${stats.flushCount}
Total Collections Fetched:${stats.collectionFetchCount}
Total Collections Loaded:${stats.collectionLoadCount}
Total Entities Fetched:${stats.entityFetchCount}
Total Entities Loaded:${stats.entityFetchCount}
Total Queries:${stats.queryExecutionCount}
queryCacheHitCount:${queryCacheHitCount}
queryCacheMissCount:${queryCacheMissCount}
queryCacheHitRatio:${queryCacheHitRatio}
######################## Hibernate Stats ##############################################
"""
        return result
    }
}
