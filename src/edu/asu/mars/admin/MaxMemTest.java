package edu.asu.mars.admin;

import com.sun.management.OperatingSystemMXBean;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class MaxMemTest {
    private static int ONE_KILOBYTE = 1024;
    private static int ONE_MEGABYTE = ONE_KILOBYTE * ONE_KILOBYTE;
    private long totalPhysMem;
    private long totalSwap;
    private long totalMem;
    private OperatingSystemMXBean os;

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getFreeMemory() {
        return Runtime.getRuntime().freeMemory() / (ONE_MEGABYTE);
    }

    private long getUsedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (ONE_MEGABYTE);
    }

    MaxMemTest() {
        os = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        totalPhysMem = os.getTotalPhysicalMemorySize();
        totalSwap = os.getTotalSwapSpaceSize();
        totalMem = totalPhysMem + totalSwap;
    }

    void RealMaxMemTest() {
        List listOfBytes = new ArrayList();
        int run = 0;

        long lastRun = new Date().getTime();
        long currentRun = new Date().getTime();
        while (true) {
            run++;
            byte[] mb = new byte[ONE_MEGABYTE];
            listOfBytes.add(mb);
            sleep(5);
            System.out.print(".");
            if (run % 40 == 0) {
                System.out.printf("Allocated: %sMB (Free: %sMB Physical, %sMB Swap)\n", getUsedMemory(), os.getFreePhysicalMemorySize() / ONE_MEGABYTE, os.getFreeSwapSpaceSize() / ONE_MEGABYTE);
                lastRun = currentRun;
                currentRun = new Date().getTime();
                System.out.println("Time since last print: " + (currentRun - lastRun) + "ms");
            }

        }
    }

    void FakeMaxMemTest() {

        try {
            String jarPath = new File(Tools.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getCanonicalPath();
            String javaBinary = System.getProperty("java.home") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "java";
            CommandLine cmdLine = new CommandLine(javaBinary);
            cmdLine.addArgument("-Xmx" + totalMem / ONE_MEGABYTE + "m");
            cmdLine.addArgument("-jar");
            cmdLine.addArgument(jarPath);
            cmdLine.addArgument("-z");
            DefaultExecutor executor = new DefaultExecutor();
            ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
            executor.setWatchdog(watchdog);
            int exitValue = executor.execute(cmdLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

