# ReadWriteLocks

# Custom Read-Write Lock in Java

This project demonstrates a custom implementation of a read-write lock in Java using synchronization mechanisms such as ReentrantLock and mutex to manage concurrent access to a shared resource by multiple reader and writer threads.

## Key Requirements

1. Multiple reader threads can access the resource concurrently.
2. Only one writer thread can access the resource at a time.
3. Writer threads must wait until all reader threads have finished before they can proceed.
4. Reader threads must wait if there is a writer thread writing or waiting to write.

## Overview

The program creates and manages multiple reader and writer threads that interact with a shared integer variable s. Writers can modify the value of s, while readers can only read its current value. Synchronization is achieved using ReentrantLock to prevent race conditions and ensure data consistency.

## Project Structure

The project is organized into the following classes, each in its own file within the ThreadsCheck package:

1. Main: Contains the main method to start the program.
2. ReadWriteLock: Manages the shared resource and synchronization mechanisms.
3. Reader: Implements the reader thread logic.
4. Writer: Implements the writer thread logic.

### Main Class

The Main class initializes the shared variable s, takes input for the number of reader and writer threads, and creates the respective threads. It handles invalid input and coordinates the creation and execution of threads.

### ReadWriteLock
 Class

The ReadWrite class defines the shared variable s, locks (mutex and wrt), and methods to manage reader and writer synchronization. It includes methods to lock and unlock the write access, increment and decrement the reader count, and retrieve the shared variable's value.

### Reader Class

The Reader class implements the Runnable interface for reader threads. Each reader thread reads the shared variable s a specified number of times. It manages the reader count and ensures proper synchronization to allow concurrent reads while preventing writes.

### Writer Class

The Writer class implements the Runnable interface for writer threads. Each writer thread modifies the shared variable s a specified number of times. It ensures that only one writer can access the shared variable at a time.

## Usage

1. Compile the Java files and run the Main class.
2. Enter the initial value of the shared variable s.
3. Enter the number of reader threads.
4. Enter the number of writer threads.
5. Follow the prompts to specify the number of read and write operations for each thread.

## Example

![input reader and writer](https://github.com/udaarbhavana/ReadWriteLocks/assets/118459224/2a6fbbc9-689e-4c73-878f-f5a6df230135)

1. User taken the initial value od shared variable 's' as 10
2. User taken no. of readers is 2 as input i.e Reader 0 and Reader 1
3. User taken no. of writers is 2 as input i.e writer 0 and writer 1
4. Now READER and WRITER threads are created successfully.

![writer 2](https://github.com/udaarbhavana/ReadWriteLocks/assets/118459224/d1746b59-4bd1-48a3-8947-ef4f81b32138)

5. Writer 1 will wait for some random time. Then user updates wants to update 2 times.
6. First time 5 is added to s, then s becomes 10+5 = 15
7. second time 5 is added to s, then s = 15+5 = 20
8. Now updated s = 20

![writer 1](https://github.com/udaarbhavana/ReadWriteLocks/assets/118459224/de07758b-e86a-4dac-bf3b-f620c59b6939)

9. Writer 1 has stopped writing and writer 0 starts writing.
10. Now also user wants to update 2 times with values 5 for first time and 5 for second time.
11. Then updated s = 20+5+5 = 30.

![reader](https://github.com/udaarbhavana/ReadWriteLocks/assets/118459224/f1045760-13c7-4e1e-a6ac-7b879a24af08)

12. Now Reader 0 and Reader 1 wait for some random times. Then R0 starts reading shared variable.
13. As no writer is left, Reader 0 will start reading shared variable after reader 1 is done.
14. Finally, after joining the thread, we get the shared variable value as 30.


## Notes

- The program uses ReentrantLock to manage locks for reading and writing.
- Ensure proper synchronization to avoid race conditions and ensure data consistency.

## Compilation and Execution

To compile and run the program, use the following commands:

sh
javac ThreadsCheck/*.java
java ThreadsCheck.Main


## Conclusion

This project demonstrates the implementation of a custom read-write lock in Java, highlighting the synchronization mechanisms needed to manage concurrent access to a shared resource. It provides a foundation for understanding more complex concurrency control techniques in Java.
