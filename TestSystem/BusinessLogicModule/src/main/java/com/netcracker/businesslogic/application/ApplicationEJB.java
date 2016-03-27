package com.netcracker.businesslogic.application;

import com.netcracker.businesslogic.support.DatabaseDelegateEJB;
import com.netcracker.businesslogic.support.TestingFileSupplierImpl;
import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.filesystem.supplier.StandardFileSupplier;
import com.netcracker.testing.system.MultithreadTestingSystem;
import com.netcracker.testing.system.TestingSystem;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Startup;

@Singleton
@LocalBean
@Startup
@Lock(LockType.READ)
public class ApplicationEJB {

    private static final int TESTING_SYSTEM_THREADS = 10;
    
    private TestingSystem testingSystem;
    private FileSupplier fileSupplier;
    //private MonitorPool monitorPool;
    @EJB
    private DatabaseDelegateEJB databaseDelegate;

    @PostConstruct
    public void initApplication() {
        fileSupplier = StandardFileSupplier.getDefault();
        //monitorPool = StandardMonitorPool.getDefault();
        //monitorPool.setDatabaseDelegate(databaseDelegate);
        //FileSystemDelegate fileSystemDelegate = new FileSystemDelegateImpl(fileSupplier);
        //ResultsConservator conservator = new XmlResultsConservator(fileSystemDelegate);
        //monitorPool.setResultsConservator(conservator);
        testingSystem = new MultithreadTestingSystem(TESTING_SYSTEM_THREADS,
                new TestingFileSupplierImpl(fileSupplier));
        testingSystem.start();
    }
    
    @PreDestroy
    public void stopApplication() {
        testingSystem.stop();
    }
    
    public TestingSystem getTestingSystem() {
        return testingSystem;
    }

    public FileSupplier getFileSupplier() {
        return fileSupplier;
    }
    
//    public MonitorPool getMonitorPool() {
//        return monitorPool;
//    }
    
}
