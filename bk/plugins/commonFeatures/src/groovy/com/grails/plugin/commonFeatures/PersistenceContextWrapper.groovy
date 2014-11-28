package com.grails.plugin.commonFeatures

import org.codehaus.groovy.grails.support.PersistenceContextInterceptor

/**
 * Wraps the execution of a Runnable in a persistence context, via the persistenceInterceptor.
 */
class PersistenceContextWrapper {

    private final PersistenceContextInterceptor persistenceInterceptor

    PersistenceContextWrapper(PersistenceContextInterceptor persistenceInterceptor) {
        this.persistenceInterceptor = persistenceInterceptor
    }

    protected wrap(Closure wrapped) {
        persistenceInterceptor.init()
        try {
            wrapped()
        } finally {
            persistenceInterceptor.flush()
            persistenceInterceptor.destroy()
        }
    }
}