package dev.wirezbungee.utils.files.lang;

import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezmc.files.Lang;

import java.util.concurrent.atomic.AtomicReference;

public class LangAccessor {

    public static String toConfigString(Lang item) {
        AtomicReference<String> atomicString = new AtomicReference<>();
        AbstractModuleLoader.getModule(LangFile.class).ifPresent(langFile ->
                atomicString.set(langFile.getConfiguration().getString(item.getPath(), item.getValue())));
        return atomicString.get();
    }
}
