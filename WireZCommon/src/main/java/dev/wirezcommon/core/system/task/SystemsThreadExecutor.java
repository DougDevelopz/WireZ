package dev.wirezcommon.core.system.task;

import java.util.concurrent.*;

public class SystemsThreadExecutor implements AutoCloseable {

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName("wirez-systems-monitor");
        thread.setDaemon(true);
        return thread;
    });

    public static void call() {
        executor.scheduleAtFixedRate(new SystemsTask(), 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        if (executor.isTerminated()) {
            return;
        }

        executor.shutdownNow();
    }
}
