package com.grails.plugin.commonFeatures

import groovy.util.logging.Log4j
import org.codehaus.groovy.grails.support.PersistenceContextInterceptor

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

@Log4j
class PersistenceContextExecutorWrapper {

    // Autowired
    @Delegate
    ExecutorService executor
    PersistenceContextInterceptor persistenceInterceptor

    void execute(Runnable command) {
        executor.execute(inPersistence(command))
    }

    public <T> Future<T> submit(Callable<T> task) {
        executor.submit(inPersistence(task))
    }

    Future<?> submit(Runnable task) {
        executor.submit(inPersistence(task))
    }

    public <T> Future<T> submit(Runnable task, T result) {
        executor.submit(inPersistence(task), result)
    }

    Future withPersistence(Closure task) {
        executor.submit(inPersistence((Closure) task))
    }

    Runnable inPersistence(Runnable task) {
        if (persistenceInterceptor == null) {
            throw new IllegalStateException("Unable to create persistence context wrapped runnable because persistenceInterceptor is null")
        }

        new PersistenceContextRunnableWrapper(persistenceInterceptor, task)
    }

    public void destroy() {
        executor.shutdown()
        if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
            log.warn "ExecutorService did not shutdown in 2 seconds. Forcing shutdown of any scheduled tasks"
            executor.shutdownNow()
        }
    }

}
