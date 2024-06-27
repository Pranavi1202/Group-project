package ReaderWriterenhanced;

import java.util.Scanner;

public class Writer implements Runnable {
    private final ReadWriteLock sharedResource;
    private final int id;

    public Writer(ReadWriteLock sharedResource, int id) {
        this.sharedResource = sharedResource;
        this.id = id;
    }

    @Override
    public void run() {
        sharedResource.lockWrite();
        try {
            int randomDelay = (int) (Math.random() * 10);
            System.out.println("--------------------------------------------------");
            System.out.println("Writer " + id + " Waiting for random time between 0ns and 10ns = " + randomDelay);
            Thread.sleep(randomDelay * 1000L);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of times Writer " + id + " wants to write:");
            int writeTimes = scanner.nextInt();
            System.out.println("Writer " + id + " is writing...");
            for (int j = 0; j < writeTimes; j++) {
                System.out.println("Enter the " + (j + 1) + "th integer value to write:");
                int valueToAdd = scanner.nextInt();
                sharedResource.incrementSharedValue(valueToAdd);
            }
            System.out.println("Updated value of shared variable 's' = " + sharedResource.getSharedValue());
            System.out.println("--------------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sharedResource.unlockWrite();
            System.out.println("Writer " + id + " has stopped.");
        }
    }
}
