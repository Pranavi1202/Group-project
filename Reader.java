package ReaderWriterenhanced;

public class Reader implements Runnable {
    private final ReadWriteLock sharedResource;
    private final int id;

    public Reader(ReadWriteLock sharedResource, int id) {
        this.sharedResource = sharedResource;
        this.id = id;
    }

    @Override
    public void run() {
        sharedResource.lockRead();
        try {
            int randomDelay = (int) (Math.random() * 10);
            System.out.println("Reader " + id + " Waiting for random time between 0ns and 10ns = " + randomDelay);
            Thread.sleep(randomDelay * 1000L);

            System.out.println("Reader " + id + " is reading...");
            System.out.println("Reader " + id + " reads the shared value = " + sharedResource.getSharedValue());
            System.out.println("Number of readers present = " + sharedResource.getReaderCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sharedResource.unlockRead();
            System.out.println("Reader " + id + " has stopped.");
        }
    }
}
