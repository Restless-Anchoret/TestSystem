package com.netcracker.businesslogic.application;

import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.support.DatabaseDelegateEJB;
import com.netcracker.businesslogic.support.FileSystemDelegateImpl;
import com.netcracker.businesslogic.support.TestingFileSupplierImpl;
import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.filesystem.supplier.StandardFileSupplier;
import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.conservator.XmlResultsConservator;
import com.netcracker.monitoring.delegate.FileSystemDelegate;
import com.netcracker.monitoring.monitor.MonitorPool;
import com.netcracker.monitoring.monitor.StandardMonitorPool;
import com.netcracker.testing.system.MultithreadTestingSystem;
import com.netcracker.testing.system.TestingSystem;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
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

    @Resource(name = "adminDefaultLogin")
    private String adminDefaultLogin;
    @Resource(name = "adminDefaultPassword")
    private String adminDefaultPassword;
    @Resource(name = "fileSystemPath")
    private String fileSystemPath;
    @Resource(name = "testingSystemThreads")
    private Integer testingSystemThreads;
    
    private TestingSystem testingSystem;
    private FileSupplier fileSupplier;
    private MonitorPool monitorPool;
    
    @EJB(beanName = "DatabaseDelegateEJB")
    private DatabaseDelegateEJB databaseDelegateEJB;
    @EJB(beanName = "RegistrationEJB")
    private RegistrationEJB registrationEJB;

    @PostConstruct
    public void initApplication() {
        checkResources();
        registrationEJB.checkAdminRegistration(adminDefaultLogin, adminDefaultPassword);
        fileSupplier = new StandardFileSupplier(Paths.get(fileSystemPath));
        monitorPool = StandardMonitorPool.getDefault();
        monitorPool.setDatabaseDelegate(databaseDelegateEJB);
        FileSystemDelegate fileSystemDelegate = new FileSystemDelegateImpl(fileSupplier);
        ResultsConservator conservator = new XmlResultsConservator(fileSystemDelegate);
        monitorPool.setResultsConservator(conservator);
        testingSystem = new MultithreadTestingSystem(testingSystemThreads,
                new TestingFileSupplierImpl(fileSupplier));
        testingSystem.start();
    }
    
    private void checkResources() {
        if (adminDefaultLogin == null) {
            BusinessLogicLogging.logger.fine("adminDefaultLogin == null");
            adminDefaultLogin = "admin";
        }
        if (adminDefaultPassword == null) {
            BusinessLogicLogging.logger.fine("adminDefaultPassword == null");
            adminDefaultPassword = "password943";
        }
        if (fileSystemPath == null) {
            BusinessLogicLogging.logger.fine("fileSystemPath == null");
            fileSystemPath = "D:\\Dropbox\\University\\NetCracker (project)";
        }
        if (testingSystemThreads == null) {
            BusinessLogicLogging.logger.fine("testingSystemThreads == null");
            testingSystemThreads = 10;
        }
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
    
    public MonitorPool getMonitorPool() {
        return monitorPool;
    }
    
}
