package dev.wirezcommon.core.promise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PromiseGlobalExecutor {

    private static final ExecutorService globalExecutor = Executors.newFixedThreadPool(2);

    public static ExecutorService getGlobalExecutor() {
        return globalExecutor;
    }

    public static void terminate() {
        if (globalExecutor.isTerminated()) return;
        globalExecutor.shutdownNow();
    }
}
