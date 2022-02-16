package dev.wirezcommon.module;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class AbstractModuleLoader {

    private static final Map<Class<? extends AbstractModuleLoader>, AbstractModuleLoader> modules = Collections.synchronizedMap(new LinkedHashMap<>());
    private String name;
    private String description;
    private ModuleLoaderType moduleType;

    public AbstractModuleLoader() {
        addMetaFields();
    }

    private static <T> T createObject(Class<? extends T> clazz) {
        T object = null;
        try {
            object = clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return object;
    }

    public void addMetaFields() {
        ModuleLoaderInfo metadata = this.getClass().getAnnotation(ModuleLoaderInfo.class);
        name = metadata.name();
        description = metadata.description();
        moduleType = metadata.type();
    }

    public static <T extends AbstractModuleLoader> Optional<T> getModule(Class<T> clazz) {
        AbstractModuleLoader module = modules.get(clazz);

        return module != null ? Optional.of(clazz.cast(modules.get(clazz))) : Optional.empty();
    }

    public static <T extends AbstractModuleLoader> T loadModule(Class<T> clazz) {
        if (modules.containsKey(clazz))
            throw new RuntimeException("This module has already been loaded. Are you sure you want to load it twice?");

        T clazzObject = createObject(clazz);

        modules.put(clazz, clazzObject);

        return clazzObject;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleLoaderType getModuleType() {
        return moduleType;
    }

    public static Map<Class<? extends AbstractModuleLoader>, AbstractModuleLoader> getModuleMap() {
        return modules;
    }
}
