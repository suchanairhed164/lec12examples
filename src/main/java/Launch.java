public class Launch {
    public static void main(String args[]){
        MyThread t = new MyThread();
        t.start();

        Runnable r = new MyRunnable();
        Thread g = new Thread(r);
        g.start();
    }
}
