package com.netcracker.testingmodule.tester;

import com.netcracker.testingmodule.registry.AbstractRegistry;
import com.netcracker.testingmodule.registry.Suppliers;

public class ProblemTesterRegistry extends AbstractRegistry<ProblemTester> {

    private static final String CODING_ID = "coding";
    
    private static final ProblemTesterRegistry registry = new ProblemTesterRegistry();
    
    static {
        registry.put(CODING_ID, Suppliers.createSingletonSupplier(CodingProblemTester.class));
    }
    
    public static ProblemTesterRegistry registry() {
        return registry;
    }
    
    private ProblemTesterRegistry() { }

    @Override
    public ProblemTester getDefault() {
        return get(CODING_ID);
    }
    
}