package dev.wirezcommon.mysql.hikari;

import com.zaxxer.hikari.HikariConfig;
import dev.wirezcommon.mysql.other.ConnectionType;
import dev.wirezcommon.mysql.other.SQLTypes;

public interface HikariSetupTool extends ConnectionType {

    default HikariConfig getDataProperties(SQLTypes types, HikariAuthentication authentication, int timeOut, int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(types.getDriverName());
        config.setUsername(authentication.user());
        config.setPassword(authentication.password());
        config.setJdbcUrl(generateURL(types.getDriverURL(), authentication));
        config.setConnectionTimeout(timeOut);
        config.setMaximumPoolSize(poolSize);
        return config;
    }

    default String generateURL(String jdUrl, HikariAuthentication authentication) {
        String url = jdUrl.replace("{host}", authentication.host());
        url = url.replace("{port}", String.valueOf(authentication.port()));
        url = url.replace("{database}", authentication.database());
        return url;
    }
}
