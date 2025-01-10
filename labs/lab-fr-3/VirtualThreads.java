import java.util.Set;

class VirtualThreads {
    public static void main(String[] args) throws InterruptedException {

        var threads = Set.of(
                Thread.startVirtualThread(VirtualThreads::frequentIO),
                Thread.startVirtualThread(VirtualThreads::frequentIO),
                Thread.startVirtualThread(VirtualThreads::frequentIO),
                Thread.startVirtualThread(VirtualThreads::frequentIO),
                Thread.startVirtualThread(VirtualThreads::frequentIO)
        );

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Work done");
    }

    private static void frequentIO() {
        synchronized (VirtualThreads.class) {
            try {
                // Perform some blocking I/O work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}