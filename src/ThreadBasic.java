/**
 * @author by liu.hongda
 * @Description TODO
 * @Date 2019/6/26 16:31
 */

public class ThreadBasic {
    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRunnable()).start();
    }
}

/**
 * 创建线程方法一：继承线程类，我们通常不这样做，因为JAVA是单继承的，继承了这个就继承不了别的类，有限制
 */
class MyThread extends Thread{

    @Override
    public void run(){
        //引用当前线程的ID
        System.out.println(Thread.currentThread().getId());
        //引用当前线程的方法名
        System.out.println(Thread.currentThread().getName());
    }
}

/**
 * 创建线程方法二：实现RUNNABLE接口，重写唯一的run方法，通常使用这种
 */
class MyRunnable implements Runnable{

    @Override
    public void run() {
        //引用当前线程的ID
        System.out.println(Thread.currentThread().getId());
        //引用当前线程的方法名
        System.out.println(Thread.currentThread().getName());
    }
}