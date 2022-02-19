package dev.wirezcommon.socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.promise.Promise;
import dev.wirezcommon.promise.PromiseGlobalExecutor;
import dev.wirezcommon.system.SystemConstants;
import dev.wirezcommon.system.SystemsWrapper;
import dev.wirezcommon.system.module.disk.DiskMonitor;
import dev.wirezcommon.system.module.memory.MemoryMonitor;

import java.util.logging.Logger;

import static dev.wirezcommon.system.SystemConstants.*;

public class WireZServerSocketController {

    private final WireZSocketServer socketServer;
    private final Gson gson = new Gson();
    private final Logger logger = Logger.getLogger(WireZSocketServer.class.getName());

    public WireZServerSocketController(int port) {
        socketServer = new WireZSocketServer(port);
        Promise.createNew().fulfillInAsync(() -> {
            socketServer.run();
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }


    public void reportStats() {
        logger.info("DataSocketController: reportStats");
        JsonElement parent = new JsonObject();
        JsonObject jsonObject = parent.getAsJsonObject();

        for (AbstractModuleLoader module : AbstractModuleLoader.getModuleMap().values()) {
            if (!(module instanceof SystemsWrapper<?> systemModule)) continue;

            if (systemModule.getElement().getClass().isArray()) {
                if (systemModule.isThread()) continue;
                break;
            } else {
                jsonObject.addProperty(module.getName(), String.valueOf(systemModule.getElement()));
            }
        }

        jsonObject.addProperty("System CPU 10s", getSystemCPU10Sec());
        jsonObject.addProperty("System CPU 1m", getSystemCPU1Min());
        jsonObject.addProperty("System CPU 15m", getSystemCPU15Min());

        jsonObject.addProperty("Processed CPU 10s", getProcessCPU10Sec());
        jsonObject.addProperty("Processed CPU 1m", getProcessCPU1Min());
        jsonObject.addProperty("Processed CPU 15m", getProcessCPU15Min());

        AbstractModuleLoader.getModule(MemoryMonitor.class).ifPresent(memoryMonitor -> {
            double div = (memoryMonitor.getElement()[1] / memoryMonitor.getElement()[0]) * 100;
            jsonObject.addProperty(memoryMonitor.getName(), String.valueOf(div));
        });


        AbstractModuleLoader.getModule(DiskMonitor.class).ifPresent(diskMonitor -> {
            double div = (diskMonitor.getElement()[2] / diskMonitor.getElement()[0]) * 100;
            jsonObject.addProperty(diskMonitor.getName(), String.valueOf(div));
        });


        socketServer.broadcast(gson.toJson(parent));
    }
}
