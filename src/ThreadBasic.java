import java.util.Date;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

/**
 * @author by liu.hongda
 * @Description TODO
 * @Date 2019/6/26 16:31
 */

public class ThreadBasic {
    public static void main(String[] args) {
        //new MyThread().start();

        //new Thread(new MyRunnable()).start();

        //new Thread(new FutureTask<>(new MyCallable())).start();

        //new Thread(new ParentRunnable()).start();

//        new Thread(new Runnable() {
//            int sum = 0;
//            @Override
//            public void run() {
//                long beginTime=System.currentTimeMillis();
//                for (int i = 0; i < 99999; i++) {
//                    sum += 1;
//                    // yield相当于sleep与wait，但它不需要指定时间，会在线程空余时自动结束，这个方法通常不用
//                    Thread.yield();
//                }
//                long endTime=System.currentTimeMillis();
//                System.out.println("用时："+ (endTime - beginTime) + " 毫秒！");
//            }
//        }).start();

        Thread thread = new Thread() {
            @Override
            public void run() {
                IntStream.range(0, 5).forEach(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\ti=" + i);
                });
            }
        };

        thread.setDaemon(true);
        thread.start();


        for (int i = 0; i < 2; i++) {
            System.out.println(Thread.currentThread().getName() + "\ti=" + i);
        }
        System.out.println("主线程死亡，子线程也要陪着一块死！");
    }
}

/**
 * 线程sleep可在线程异步中使用,在睡眠时不会释放锁，别的线程无法进入
 */
class ThreadSleep {
    public static void main(String[] args) throws Exception {
        Thread thread0 = new Thread(() -> {
            try {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t线程睡眠程序已启动");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t线程已被唤醒");
            }
        });
        thread0.start();

        Thread.sleep(2000);
        new Thread(() -> {
            System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t线程唤醒程序已启动");
            // 无需获取锁就可以调用interrupt
            thread0.interrupt();
        }).start();
    }
}

/**
 * 线程wait只可用于同步方法中调用，在等待时会释放锁，导致别的线程可进入
 */
class ThreadWait {
    public static void main(String[] args) throws Exception {
        ThreadWait threadWait = new ThreadWait();
        new Thread(() -> {
            try {
                threadWait.printFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                threadWait.printFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t睡觉5秒中，目的是让上面的线程先执行，即先执行wait()");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //唤醒等待的方法
            threadWait.notifyPrint();
        }).start();
    }

    private synchronized void printFile() throws InterruptedException {
        System.out.println(new Date() + Thread.currentThread().getName() + "\t等待打印结果");
        this.wait();
        System.out.println(new Date() + Thread.currentThread().getName() + "\t打印结束");
    }

    private synchronized void notifyPrint() {
        //notifyAll会唤醒当前对象所有正在等待的对象，notify会随机唤醒一个
        this.notifyAll();
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t通知完成...");
    }
}

/**
 * 创建线程方法一：继承线程类，我们通常不这样做，因为JAVA是单继承的，继承了这个就继承不了别的类，有限制
 */
class MyThread extends Thread {

    @Override
    public void run() {
        //引用当前线程的ID
        System.out.println(Thread.currentThread().getId());
        //引用当前线程的方法名
        System.out.println(Thread.currentThread().getName());

        System.out.println();
    }
}

/**
 * 创建线程方法二：实现RUNNABLE接口，重写唯一的run方法，通常使用这种
 */
class MyRunnable implements Runnable {

    @Override
    public void run() {
        //引用当前线程的ID
        System.out.println(Thread.currentThread().getId());
        //引用当前线程的方法名
        System.out.println(Thread.currentThread().getName());
    }
}

/**
 * 创建线程方法三：实现Callable接口，这是带返回参数的
 */
class MyCallable implements Callable<Integer> {

    private static final int IVALUE = 100000;

    @Override
    public Integer call() throws Exception {
        //引用当前线程的ID
        System.out.println(Thread.currentThread().getId());
        //引用当前线程的方法名
        System.out.println(Thread.currentThread().getName());

        int sum = 0;
        for (int i = 0; i <= IVALUE; i++) {
            sum = sum + i;
        }
        Thread.sleep(5000);

        //引用当前线程的ID
        System.out.println(Thread.currentThread().getId());
        //引用当前线程的方法名
        System.out.println(Thread.currentThread().getName());

        return sum;
    }
}

/**
 * 调用join方法，会将join中的线程执行完毕后，再继续执行,join底层使用的是wait所以会让出锁
 */
class ParentRunnable implements Runnable {
    @Override
    public void run() {
        // 线程处于new状态
        Thread childThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "子线程 running");
                }
            }
        };
        // 线程处于runnable就绪状态
        childThread.start();
        try {
            // 当调用join时，parent会等待child执行完毕后再继续运行
            // 将某个线程加入到当前线程
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "父线程 running");
        }
    }
}

/**
 * 这一段被匿名内部类代替了
 */
//class ChildRunAble implements Runnable {
//    @Override
//    public void run() {
//        for (int i = 0; i < 2; i++) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//            }
//            System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "子线程 running");
//        }
//    }
//}
