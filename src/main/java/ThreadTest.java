/*
public class ThreadTest implements Runnable {
    public void run() {
        double calc;
        for (int i=0; i<50000; i++) {
            calc=Math.sin(i*i);
            if (i%10000==0) {
                System.out.println(getName()+
                        " counts " + i/10000);
            }
        }
    }

    public String getName() {
        return Thread.currentThread().getName();
    }
    /*
    public static void main(String s[]) {
        // Подготовка потоков
        Thread t[] = new Thread[3];
        for (int i=0; i<t.length; i++) {
            t[i]=new Thread(new ThreadTest(),
                    "Thread "+i);
        }
        // Запуск потоков
        for (int i=0; i<t.length; i++) {
            t[i].start();
            System.out.println(t[i].getName()+
                    " started");
        }
    }

     /////



    public static void main(String s[]) {
// Подготовка потоков
        Thread t[] = new Thread[3];
        for (int i=0; i<t.length; i++) {
            t[i]=new Thread(new ThreadTest(),
                    "Thread "+i);
            t[i].setPriority(Thread.MIN_PRIORITY +
                    (Thread.MAX_PRIORITY -
                            Thread.MIN_PRIORITY)/t.length*i);
        }

// Запуск потоков
        for (int i=0; i<t.length; i++) {
            t[i].start();
            System.out.println(t[i].getName()+
                    " started");
        }
    }


}



 */
/*
public class ThreadTest implements Runnable {

    // Отдельная группа, в которой будут
    // находиться все потоки ThreadTest
    public final static ThreadGroup GROUP = new ThreadGroup("Daemon demo");

    // Стартовое значение, указывается при создании объекта
    private int start;

    public ThreadTest(int s) {
        start = (s%2==0)? s: s+1;
        new Thread(GROUP, this, "Thread "+ start).start();
    }

    public void run() {
        // Начинаем обратный отсчет
        for (int i=start; i>0; i--) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
            // По достижении середины порождаем
            // новый поток с половинным начальным
            // значением
            if (start>2 && i==start/2)
            {
                new ThreadTest(i);
            }
        }
    }

    public static void main(String s[]) {
        new ThreadTest(16);
        new DaemonDemo();
    }
}
class DaemonDemo extends Thread {
    public DaemonDemo() {
        super("Daemon demo thread");
        setDaemon(true);
        start();
    }

    public void run() {
        Thread threads[]=new Thread[10];
        while (true) {
            // Получаем набор всех потоков из
            // тестовой группы
            int count=ThreadTest.GROUP.activeCount();
            if (threads.length<count) threads = new Thread[count+10];
            count=ThreadTest.GROUP.enumerate(threads);

            // Распечатываем имя каждого потока
            for (int i=0; i<count; i++) {
                System.out.print(threads[i].getName()+", ");
            }
            System.out.println();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }
    }
}

 */
/*
public class ThreadTest {

    private int a=1, b=2;
    public void one() {
        a=b;
    }
    public void two() {
        b=a;
    }

    public static void main(String s[]) {
        int a11=0, a22=0, a12=0;
        for (int i=0; i<1000; i++) {
            final ThreadTest o = new ThreadTest();

            // Запускаем первый поток, который
            // вызывает один метод
            new Thread() {
                public void run() {
                    o.one();
                }
            }.start();

            // Запускаем второй поток, который
            // вызывает второй метод
            new Thread() {
                public void run() {
                    o.two();
                }
            }.start();

            // даем потокам время отработать
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

            // анализируем финальные значения
            if (o.a==1 && o.b==1) a11++;
            if (o.a==2 && o.b==2) a22++;
            if (o.a!=o.b) a12++;
        }
        System.out.println(a11+" "+a22+" "+a12);
    }
}

 */
/*
public class ThreadTest implements Runnable {
    private static ThreadTest
            shared = new ThreadTest();
    public void process() {
        for (int i=0; i<3; i++) {
            System.out.println(
                    Thread.currentThread().
                            getName()+" "+i);
            Thread.yield();
        }
    }

    //public void run() {
    //    shared.process();
    //}
    public void run() {
        synchronized (shared) {
            shared.process();
        }
    }
    public static void main(String s[]) {
        for (int i=0; i<3; i++) {
            new Thread(new ThreadTest(),
                    "Thread-"+i).start();
        }
    }
}

 */
public class ThreadTest implements Runnable {
    final static private Object shared=new Object();
    private int type;
    public ThreadTest(int i) {
        type=i;
    }

    public void run() {
        if (type==1 || type==2) {
            synchronized (shared) {
                try {
                    shared.wait();
                } catch (InterruptedException e) {}
                System.out.println("Thread "+type+" after wait()");
            }
        } else {
            synchronized (shared) {
                shared.notifyAll();
                System.out.println("Thread "+type+" after notifyAll()");
            }
        }
    }

    public static void main(String s[]) {
        ThreadTest w1 = new ThreadTest(1);
        new Thread(w1).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        ThreadTest w2 = new ThreadTest(2);
        new Thread(w2).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        ThreadTest w3 = new ThreadTest(3);
        new Thread(w3).start();
    }
}