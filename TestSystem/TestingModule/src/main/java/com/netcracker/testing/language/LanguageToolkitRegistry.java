package com.netcracker.testing.language;

import com.netcracker.testing.registry.AbstractRegistry;
import com.netcracker.testing.registry.Suppliers;

public class LanguageToolkitRegistry extends AbstractRegistry<LanguageToolkit> {

    private static final String JAVA_ID = "java";
    
    private static final LanguageToolkitRegistry registry = new LanguageToolkitRegistry();
    
    static {
        registry.put(JAVA_ID, Suppliers.createSingletonSupplier(JavaLanguageToolkit.class));
    }
    
    public static LanguageToolkitRegistry registry() {
        return registry;
    }
    
    private LanguageToolkitRegistry() { }

    @Override
    public LanguageToolkit getDefault() {
        return get(JAVA_ID);
    }
    
}