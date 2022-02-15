package dev.wirezcommon.core.system.module.database;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.minecraft.mysql.MultiDataPoolSetup;
import dev.wirezcommon.core.system.SystemsWrapper;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ModuleLoaderInfo(name = "Active Connections Monitor", description = "Active Connections Monitor System for database aspects of the plugin", type = ModuleLoaderType.ADDON)
public class ActiveConnections extends AbstractModuleLoader implements SystemsWrapper<Integer> {

    @Override
    public boolean isThread() {
        return false;
    }

    @Override
    public boolean isOperating() {
        return false;
    }

    @Override
    public boolean isMemory() {
        return false;
    }

    @Override
    public boolean isDatabase() {
        return true;
    }

    @Override
    public Integer getElement() {
        AtomicInteger fetchedValue = new AtomicInteger();
        if (MultiDataPoolSetup.grabInstance().getPlayersCurrentDbs().isEmpty()) {
            return -1;
        }
        for (Map<String, HikariDataSource> dataSourceMap : MultiDataPoolSetup.grabInstance().getPlayersCurrentDbs().values()) {
            for (int i = 0; i < dataSourceMap.values().size(); i++) {
                HikariPoolMXBean pool = initHikariPool(dataSourceMap.values().stream().toList().get(i));
                fetchedValue.getAndAdd(pool.getActiveConnections());
            }
        }

        return fetchedValue.get();
    }
}
