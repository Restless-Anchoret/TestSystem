package com.netcracker.testingmodule.tester;

import com.netcracker.testingmodule.system.TestingFileSupplier;
import com.netcracker.testingmodule.system.TestingInfo;

public interface ProblemTester {

    void performTesting(TestingFileSupplier fileSupplier, TestingInfo info);

}