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
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new SystemsTask(), 1, 1, TimeUnit.SECONDS);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (executor.isShutdown()) {
            return;
        }

        executor.shutdown();
    }
}
