package com.grails.plugin.commonFeatures

import org.codehaus.groovy.grails.support.PersistenceContextInterceptor

/**
 * Wraps the execution of a Runnable in a persistence context, via the persistenceInterceptor.
 */
class PersistenceContextRunnableWrapper<T> extends PersistenceContextWrapper implements Runnable {

    private final Runnable runnable

    PersistenceContextRunnableWrapper(PersistenceContextInterceptor persistenceInterceptor, Runnable runnable) {
        super(persistenceInterceptor)
        this.runnable = runnable
    }

    void run() {
        wrap { runnable.run() }
    }
}