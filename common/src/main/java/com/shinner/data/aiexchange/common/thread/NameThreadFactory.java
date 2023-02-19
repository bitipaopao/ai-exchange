package com.shinner.data.aiexchange.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class NameThreadFactory implements ThreadFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ThreadGroup threadGroup;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public NameThreadFactory(String name) {
        SecurityManager securityManager = System.getSecurityManager();
        this.threadGroup = (securityManager != null)
                ? securityManager.getThreadGroup()
                : Thread.currentThread().getThreadGroup();
        if (name == null || "".equals(name.trim())) {
            name = "pool";
        }
        this.namePrefix = name + "-thread-";
        this.uncaughtExceptionHandler = (thread, throwable) ->
                logger.error(thread.getName() + " runtime error, ", throwable.getMessage());
    }

    public NameThreadFactory(String name, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        SecurityManager s = System.getSecurityManager();
        this.threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (name == null || "".equals(name.trim())) {
            name = "pool";
        }
        this.namePrefix = name + "-thread-";
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(this.threadGroup, runnable,
                this.namePrefix + this.threadNumber.getAndIncrement());
        if (this.uncaughtExceptionHandler != null) {
            thread.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
        }
        if (thread.isDaemon()) thread.setDaemon(false);
        if (thread.getPriority() != Thread.NORM_PRIORITY)
            thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }

}
