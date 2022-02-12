package dev.wirezbukkit.utils.files.lang;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.minecraft.files.Lang;

import java.util.concurrent.atomic.AtomicReference;

public class LangAccessor {


    public static String toConfigString(Lang item) {
        AtomicReference<String> atomicString = new AtomicReference<>();
        AbstractModuleLoader.getModule(LangFile.class).ifPresent(langFile ->
                atomicString.set(langFile.getFileConfiguration().getString(item.getPath(), item.getValue())));
        return atomicString.get();
    }
}
