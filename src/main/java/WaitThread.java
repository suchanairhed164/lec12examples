public class WaitThread implements Runnable {
    private Object shared;

    public WaitThread(Object o) {
        shared=o;
    }

    public void run() {
        synchronized (shared) {
            try {
                shared.wait();
            } catch (InterruptedException e) {}
            System.out.println("after wait");
        }
    }

    public static void main(String s[]) {
        Object o = new Object();
        WaitThread w = new WaitThread(o);
        new Thread(w).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        System.out.println("before notify");
        synchronized (o) {
            o.notifyAll();
        }
    }
}
