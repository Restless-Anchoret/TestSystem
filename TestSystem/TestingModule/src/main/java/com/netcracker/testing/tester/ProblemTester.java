package com.netcracker.testing.tester;

import com.netcracker.testing.system.TestingFileSupplier;
import com.netcracker.testing.system.TestingInfo;

public interface ProblemTester {

    void performTesting(TestingFileSupplier fileSupplier, TestingInfo info);

}