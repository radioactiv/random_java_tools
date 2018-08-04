package edu.asu.mars.admin;

/**
 * User: npiace
 * Date: 10/11/17
 * Time: 12:56 PM
 */
class CpuTest {
    void runTest(Integer seconds) {
        System.out.println("Starting CPU test for " + seconds + " seconds");
        int numCore = Runtime.getRuntime().availableProcessors();
        System.out.println("System has " + numCore + " processors");
        int numThreadsPerCore = 5;
        double load = 0.8; //target load per core
        final long duration = seconds * 1000; // Convert seconds to ms
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }
    }

    /**
     * Thread that actually generates the given load
     *
     * @author Sriram
     * https://caffinc.github.io/2016/03/cpu-load-generator/
     */
    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         *
         * @param name     Name of this thread
         * @param load     Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
