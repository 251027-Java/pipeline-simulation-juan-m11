// package com.revature.lab;

import java.io.File;
import java.io.IOException;

public class PipelineSimulation {

    public static void main(String[] args) {
        System.out.println("--- Starting Pipeline ---");

        // Stage 1: Build (Compile)
        if (!runStage("Build", () -> checkSourceCode()))
            return;

        // Stage 2: Test
        if (!runStage("Test", () -> runRandomTests()))
            return;

        // Stage 3: Package
        if (!runStage("Package", () -> createArtifact()))
            return;
        
        // Stage 4: Deploy
        if (!runStage("Deploy", () -> deployArtifact()))
            return;

        System.out.println("--- Pipeline SUCCESS ---");
    }

    private static boolean runStage(String name, Supplier<Boolean> task) {
        System.out.println("Running Stage: " + name + "...");
        boolean result = task.get();

        if (!result) {
            System.out.println(name + " Stage FAILED. Stopping pipeline.");
            return false;
        }

        System.out.println(name + " Stage PASSED.");
        return true;
    }

    // Build
    private static boolean checkSourceCode() {
        File file = new File("source_code.txt");
        if (file.exists()) {
            System.out.println("Compilation Success");
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }

    // Test
    private static boolean runRandomTests() {
        boolean pass = Math.random() >= 0.5;

        if (pass) {
            System.out.println("Tests Passed");
            return true;
        } else {
            System.out.println("Tests Failed");
            return false;
        }
    }

    // Package
    private static boolean createArtifact() {
        try {
            File artifact = new File("artifact.jar");
            artifact.createNewFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Deploy
    private static boolean deployArtifact() {
        File deployDir = new File("deploy");
        if (!deployDir.exists()) {
            deployDir.mkdir();
        }

        File artifact = new File("artifact.jar");
        File deployed = new File(deployDir, "artifact.jar");

        return artifact.renameTo(deployed);
    }

    interface Supplier<T> {
        T get();
    }
}
