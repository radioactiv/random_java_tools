package edu.asu.mars.admin;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

class OpenFilesLimit {
    private class FileLockMeta {
        FileLock lock;
        FileChannel chanel;
        public File file;
    }

    private String testDir;

    private ArrayList<FileLockMeta> data = new ArrayList<FileLockMeta>();

    void findOpenFilesLimit() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        testDir = tmpDir + "/FileLimitTest";
        int openedFiles = 1;
        boolean go = true;
        setupEnv();
        System.out.println("Testing Maximum Open File Descriptors");
        while (go) {
            try {
                if (openedFiles % 100 == 0) {
                    System.out.print('.');
                }
                if (openedFiles % 5000 == 0) {
                    System.out.print(openedFiles + "\n");
                }
                openLock(openedFiles);
                openedFiles++;

            } catch (IOException ioe) {
                go = false;
            }
        }
        cleanup();
        System.out.println("Done\n\nMaximum Open File Descriptors is: " + openedFiles);
    }

    private void setupEnv() {
        new File(testDir).mkdir();
    }

    private void cleanup() {
        for (FileLockMeta fileLockMeta : data) {
            try {
                fileLockMeta.lock.release();
                fileLockMeta.chanel.close();
                fileLockMeta.file.delete();
            } catch (Exception e) {

            }
        }
        new File(testDir).delete();
    }

    private void openLock(int i) throws IOException {
        String path = testDir + "/" + i + ".file";
        // Get a file channel for the file
        FileLockMeta flm = new FileLockMeta();
        flm.file = new File(path);
        flm.chanel = new RandomAccessFile(flm.file, "rw").getChannel();
        // Use the file channel to create a lock on the file.
        // This method blocks until it can retrieve the lock.
        flm.lock = flm.chanel.lock();
        data.add(flm);
    }
}
