package com.netcracker.filesystem.temp;

import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.filesystem.supplier.StandardFileSupplier;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        FileSupplier supplier = new StandardFileSupplier(Paths.get("D:\\"));
        System.out.println(supplier.addProblemFolder("1"));
        System.out.println(supplier.addAuthorDecisionFolder("1", "1"));
        System.out.println(supplier.addCompetitionFolder("1"));
        System.out.println(supplier.addSubmissionFolder("1"));
        System.out.println(supplier.getProblemStatement("1"));
    }
    
}