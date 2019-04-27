package com.liushu.test.performance.queueperformance;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liushu on 2019/4/27.
 */
public class QPExecutor {

    private final static int THREAD_NUM = 1;

    ExecutorService procedures = Executors.newFixedThreadPool(THREAD_NUM);
    ExecutorService consumer = Executors.newFixedThreadPool(THREAD_NUM);

    private volatile boolean start = false;
    private volatile boolean stop = false;

    private long t1;
    private long t2;

    private Queue<String> queue;

    public QPExecutor(Queue queue) {
        this.queue = queue;
    }

    private int count;

    /**
     * 为了不带来其他开销，只是一个近似的qps
     * @param qps
     * @param time
     */
    public void runWithQps(int qps){

        final long sleepTime = THREAD_NUM*1000/qps;

        for(int i=0;i<THREAD_NUM;i++){
            procedures.submit(new Runnable() {
                public void run() {
                    while (!start){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    while (!stop){
                        queue.offer("test");
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            consumer.submit(new Runnable() {
                public void run() {
                    while (!start){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    while (!stop){

                        if (queue.poll()!=null){
                                count++;
                        }

                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        t1 = System.currentTimeMillis();
        start = true;
    }

    public void runWithNum(int num){

    }

    public void watch(){
        System.out.println("qps:"+count*1000/(System.currentTimeMillis()-t1));
        System.out.println("size:"+queue.size());//可能有额外开销
    }

    public void stop(){
        this.stop = true;
        t2 = System.currentTimeMillis();

        System.out.println("耗时:"+(t2-t1));
        System.out.println("count:"+count);
        System.out.println("size:"+queue.size());

    }

}
