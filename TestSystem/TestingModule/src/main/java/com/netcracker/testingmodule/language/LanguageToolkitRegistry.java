package com.netcracker.testingmodule.language;

import com.netcracker.testingmodule.registry.AbstractRegistry;
import com.netcracker.testingmodule.registry.Suppliers;

public class LanguageToolkitRegistry extends AbstractRegistry<LanguageToolkit> {

    private static final String javaId = "java";
    
    private static final LanguageToolkitRegistry registry = new LanguageToolkitRegistry();
    
    static {
        registry.put(javaId, Suppliers.createSingletonSupplier(JavaLanguageToolkit.class));
    }
    
    public static LanguageToolkitRegistry registry() {
        return registry;
    }
    
    private LanguageToolkitRegistry() { }

    @Override
    public LanguageToolkit getDefault() {
        return get(javaId);
    }
    
}