package dev.wirezcommon.core.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleLoaderInfo {

    String name();

    String description();

    ModuleLoaderType type();
}
