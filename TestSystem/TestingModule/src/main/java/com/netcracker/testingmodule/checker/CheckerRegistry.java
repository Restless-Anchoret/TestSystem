package com.netcracker.testingmodule.checker;

import com.netcracker.testingmodule.registry.AbstractRegistry;
import com.netcracker.testingmodule.registry.Suppliers;

public class CheckerRegistry extends AbstractRegistry<Checker> {

    private static final String MATCH_ID = "match";
    
    private static final CheckerRegistry registry = new CheckerRegistry();
    
    static {
        registry.put(MATCH_ID, Suppliers.createSingletonSupplier(MatchChecker.class));
    }
    
    public static CheckerRegistry registry() {
        return registry;
    }
    
    private CheckerRegistry() { }

    @Override
    public Checker getDefault() {
        return get(MATCH_ID);
    }
    
}