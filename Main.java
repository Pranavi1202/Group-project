package ReaderWriterenhanced;
/* Problem 2 : Implement a custom Read-Write Lock in Java requires managing the synchronization between reader and
               writer threads. The key requirements are:
         Multiple reader threads can access the resource concurrently.
         Only one writer thread can access the resource at a time.
         Writer threads must wait until all reader threads have finished before they
        can proceed.
         Reader threads must wait if there is a writer thread writing or waiting to
        write.
*/

/* The goal of the program is to demonstrate how multiple threads (readers and writers) interact with a shared resource
  (s) using synchronization mechanisms (ReentrantLock and mutex) to prevent race conditions and ensure data consistency.
*/

/* Shared Variable (s): This is the integer variable that both readers and writers access. Writers can modify (add)
   to this variable, while readers can only read its current value.
*/



import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Initialize the shared resource with a custom ReadWriteLock
        ReadWriteLock sharedResource = new ReadWriteLock();
        System.out.println("Enter the initial value of the shared variable 's': ");
        int initialValue = scanner.nextInt();
        sharedResource.setSharedValue(initialValue);
        System.out.println("---------------------------------------------");

        // Input number of readers
        System.out.println("Enter the number of Readers:");
        int numberOfReaders = scanner.nextInt();
        for (int i = 0; i < numberOfReaders; i++) {
            System.out.println("Reader " + i);
        }
        System.out.println("---------------------------------------------");

        // Input number of writers
        System.out.println("Enter the number of Writers:");
        int numberOfWriters = scanner.nextInt();
        for (int i = 0; i < numberOfWriters; i++) {
            System.out.println("Writer " + i);
        }
        System.out.println("---------------------------------------------");

        // Check for invalid input and handle accordingly
        if (numberOfReaders < 0 || numberOfWriters < 0) {
            System.out.println("Error: Negative count of Readers or Writers.");
            System.out.println("Program terminated.");
            return;
        }

        // Create arrays to hold reader and writer threads
        Thread[] readerThreads = new Thread[numberOfReaders];
        Thread[] writerThreads = new Thread[numberOfWriters];

        // Creating and starting writer threads
        for (int i = 0; i < numberOfWriters; i++) {
            writerThreads[i] = new Thread(new Writer(sharedResource, i));
            writerThreads[i].start();
        }

        // Creating and starting reader threads
        for (int i = 0; i < numberOfReaders; i++) {
            readerThreads[i] = new Thread(new Reader(sharedResource, i));
            readerThreads[i].start();
        }

        // Waiting for all threads to finish
        for (Thread writerThread : writerThreads) {
            writerThread.join();
        }

        for (Thread readerThread : readerThreads) {
            readerThread.join();
        }

        // After all threads have finished, print the final value of the shared variable
        System.out.println("-------------After joining the threads---------");
        System.out.println("Final value of shared variable 's' = " + sharedResource.getSharedValue());
    }
}
