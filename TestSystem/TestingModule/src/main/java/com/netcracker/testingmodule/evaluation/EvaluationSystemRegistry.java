package com.netcracker.testingmodule.evaluation;

import com.netcracker.testingmodule.registry.AbstractRegistry;
import com.netcracker.testingmodule.registry.Suppliers;

public class EvaluationSystemRegistry extends AbstractRegistry<EvaluationSystem> {

    private static final String ICPC_ID = "icpc";
    private static final String IOI_ID = "ioi";
    private static final String CHECK_ID = "check";
    
    private static final EvaluationSystemRegistry registry = new EvaluationSystemRegistry();
    
    static {
        registry.put(ICPC_ID, Suppliers.createSingletonSupplier(ICPCEvaluationSystem.class));
        registry.put(IOI_ID, Suppliers.createSingletonSupplier(IOIEvaluationSystem.class));
        registry.put(CHECK_ID, Suppliers.createSingletonSupplier(CheckEvaluationSystem.class));
    }
    
    public static EvaluationSystemRegistry registry() {
        return registry;
    }
    
    private EvaluationSystemRegistry() { }

    @Override
    public EvaluationSystem getDefault() {
        return get(ICPC_ID);
    }
    
}