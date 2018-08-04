package edu.asu.mars.admin;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

class FileLockTest {

    //Test locking a file in the system's tmp directory
    boolean testLock() {
        return doLock(System.getProperty("java.io.tmpdir") + "/locktest.file");
    }

    //Test locking a file provided to the class
    boolean testLock(String path) {
        return doLock(path);
    }

    //Actually do the lock test
    private boolean doLock(String path) {
        try {
            System.out.println("Trying to get file lock on file: '" + path + "'");
            // Get a file channel for the file
            File file = new File(path);
            FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
            // Use the file channel to create a lock on the file.  This method blocks until it can retrieve the lock.
            FileLock lock = channel.lock();
            // Try acquiring the lock without blocking. This method returns null or throws an exception if the file is already locked.
            try {
                lock = channel.tryLock();
            } catch (OverlappingFileLockException e) {
                // File is already locked in this thread or virtual machine
            }
            lock.release();// Release the lock
            channel.close();// Close the file
            System.out.println("Lock Succeeded.");
            file.delete();// Clean up after ourselves
            return true;
        } catch (ArrayIndexOutOfBoundsException iobe) {
            //Don't see how this can be the reason for this exception, Java should handle not passing an argument to a function
            System.err.println("No File Argument Passed.");
            return false;
        } catch (Exception e) {
            System.err.println("Error:");
            e.printStackTrace();
            return false;
        }
    }
}
