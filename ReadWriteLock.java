package ReaderWriterenhanced;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLock {
    private final Lock mutex = new ReentrantLock(); // Lock for controlling access to reader count
    private final Lock writeLock = new ReentrantLock(); // Lock for controlling write access
    private int sharedValue; // Shared integer variable
    private int readerCount = 0; // Number of active readers

    // Set the initial value of the shared variable
    public void setSharedValue(int value) {
        this.sharedValue = value;
    }

    // Get the current value of the shared variable
    public int getSharedValue() {
        return this.sharedValue;
    }

    // Increment the shared variable by a given value
    public void incrementSharedValue(int value) {
        this.sharedValue += value;
    }

    // Acquire the write lock
    public void lockWrite() {
        writeLock.lock();
    }

    // Release the write lock
    public void unlockWrite() {
        writeLock.unlock();
    }

    // Acquire the read lock
    public void lockRead() {
        mutex.lock();
        try {
            readerCount++;
            if (readerCount == 1) {
                writeLock.lock();
            }
        } finally {
            mutex.unlock();
        }
    }

    // Release the read lock
    public void unlockRead() {
        mutex.lock();
        try {
            readerCount--;
        } finally {
            mutex.unlock();
        }
    }

    // Get the current reader count
    public int getReaderCount() {
        return readerCount;
    }
}
