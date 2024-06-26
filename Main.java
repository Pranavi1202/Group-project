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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final Lock mutex = new ReentrantLock(); // Lock for controlling access to reader count
    private static final Lock wrt = new ReentrantLock();   // Lock for controlling write access
    private static int s;                                 // Shared integer variable
    private static int rcount = 0;                         // Number of active readers

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the shared variable 's'
        System.out.println("Enter the initial value of the shared variable 's': ");
        s = scanner.nextInt();
        System.out.println("---------------------------------------------");

        // Input number of readers
        System.out.println("Enter the number of Readers:");
        int rn = scanner.nextInt();
        for (int i = 0; i < rn; i++) {
            System.out.println("R" + i);
        }
        System.out.println("---------------------------------------------");

        // Input number of writers
        System.out.println("Enter the number of Writers:");
        int wn = scanner.nextInt();
        for (int i = 0; i < wn; i++) {
            System.out.println("W" + i);
        }
        System.out.println("---------------------------------------------");

        // Check for invalid input and handle accordingly
        if (rn < 0 || wn < 0) { // Invalid input: negative number of readers or writers
            // Print error message and terminate the program
            System.out.println("Error: Negative count of Readers or Writers.");
            System.out.println("Program terminated.");
            return;
        } else if (rn == 0) { // No readers specified
            // Print message indicating no readers will be created
            System.out.println("No Readers specified.");
            System.out.println("Reader threads will not be created.");
        } else if (wn == 0) { // No writers specified
            // Print message indicating no writers will be created
            System.out.println("No Writers specified.");
            System.out.println("Writer threads will not be created.");
        } else { // Valid input: create threads
            System.out.println("Creating threads...");
        }
        System.out.println("---------------------------------------------");

        // Create arrays to hold reader and writer threads
        Thread[] readers = new Thread[rn];
        Thread[] writers = new Thread[wn];

        // Starting threads based on the comparison of reader and writer counts
        if (wn == rn) { // Equal number of writers and readers
            // Create and start threads for each reader and writer
            for (int i = 0; i < wn; i++) {
                writers[i] = new Thread(new Writer(i)); // Create writer thread with ID i
                readers[i] = new Thread(new Reader(i));
                writers[i].start(); // Start writer thread
                readers[i].start();
            }
            // Wait for all threads to finish
            for (int i = 0; i < wn; i++) {
                try {
                    writers[i].join(); // Wait for writer thread i to finish
                    readers[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle interruption exceptions
                }
            }
        } else if (wn > rn) { // More writers than readers
            // Create and start threads for each reader and writer up to the number of readers
            for (int i = 0; i < rn; i++) {
                writers[i] = new Thread(new Writer(i));
                readers[i] = new Thread(new Reader(i));
                writers[i].start();
                readers[i].start();
            }
            // // Create and start additional writer threads beyond the number of readers
            for (int i = rn; i < wn; i++) {
                writers[i] = new Thread(new Writer(i));
                writers[i].start();
            }
            // Wait for all threads to finish
            for (int i = 0; i < rn; i++) {
                try {
                    writers[i].join();
                    readers[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Wait for remaining writer threads to finish
            for (int i = rn; i < wn; i++) {
                try {
                    writers[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else { // More readers than writers
            // Create and start threads for each reader and writer up to the number of writers
            for (int i = 0; i < wn; i++) {
                writers[i] = new Thread(new Writer(i));
                readers[i] = new Thread(new Reader(i));
                writers[i].start();
                readers[i].start();
            }
            // Create and start additional reader threads beyond the number of writers
            for (int i = wn; i < rn; i++) {
                readers[i] = new Thread(new Reader(i));
                readers[i].start();
            }
            // Wait for all threads to finish
            for (int i = 0; i < wn; i++) {
                try {
                    writers[i].join();
                    readers[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Wait for remaining reader threads to finish
            for (int i = wn; i < rn; i++) {
                try {
                    readers[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // After all threads have finished, printing the final value of the shared variable
        System.out.println("-------------After joining the threads---------");
        System.out.println("Final value of shared variable 's' = " + s);
    }

    // Writer class implementing Runnable for writing to the shared variable
    static class Writer implements Runnable {
        private final int id;

        Writer(int id) {
            this.id = id; // Writer ID
        }

        @Override
        public void run() {
            wrt.lock(); // Acquire write lock
            try {
                int n = (int) (Math.random() * 10); // Random wait time
                System.out.println("--------------------------------------------------");
                System.out.println("W" + id + " Waiting for random time between 0ns and 10ns = " + n);
                Thread.sleep(n * 1000L); // Sleep for random time

                // Get the number of times to write
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the number of times W" + id + " wants to write:");
                int t = scanner.nextInt();
                System.out.println("W" + id + " is writing..."); // Writing operation
                for (int j = 0; j < t; j++) {
                    System.out.println("Enter the " + (j + 1) + "th integer value to write:");
                    int u = scanner.nextInt();
                    s = s + u; // Update shared variable
                }
                System.out.println("Updated value of shared variable 's' = " + s);
                System.out.println("--------------------------------------------------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                wrt.unlock(); // Release write lock
            }
        }
    }

    // Reader class implementing Runnable for reading the shared variable
    static class Reader implements Runnable {
        private final int id;

        Reader(int id) {
            this.id = id; // Reader ID
        }

        @Override
        public void run() {
            mutex.lock(); // Acquire mutex to update reader count
            try {
                rcount++; // Increment reader count
                if (rcount == 1) {
                    wrt.lock(); // First reader acquires write lock to block writers
                }
            } finally {
                mutex.unlock(); // Release mutex
            }

            try {
                int n = (int) (Math.random() * 10); // Random wait time
                System.out.println("R" + id + " Waiting for random time between 0ns and 10ns = " + n);
                Thread.sleep(n * 1000L); // Sleep for random time

                // Get the number of times to read
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the number of times R" + id + " wants to read:");
                int t = scanner.nextInt();
                System.out.println("R" + id + " is reading..."); // Reading operation
                for (int j = 0; j < t; j++) {
                    System.out.println("R" + id + " reads the shared value = " + s);
                }
                System.out.println("Number of readers present = " + rcount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.lock(); // Acquire mutex to update reader count
                try {
                    rcount--; // Decrement reader count
                    if (rcount == 0) {
                        wrt.unlock(); // Last reader releases write lock to allow writers
                    }
                } finally {
                    mutex.unlock(); // Release mutex
                }
            }
        }
    }
}
