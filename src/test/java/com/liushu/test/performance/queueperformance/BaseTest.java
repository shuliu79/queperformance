package com.liushu.test.performance.queueperformance;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by liushu on 2019/4/27.
 */
public class BaseTest {

    /**
     * LinkedBlockingQueue：
     * 1860 qps,2～3%cpu，极少量内存（100s<0.1m）
     * 4700 qps,4～5%cpu，极少量内存
     * 8550 qps,8~12%cpu,极少量内存(0.2m)
     * 11100 qps,9~13%cpu,有一定内存开销，但是不确定是否是队引起
     *
     * ArrayBlockingQueue：
     * 1860 qps,2～3%cpu，极少量内存（100s<0.1m）
     * 4700 qps,5～7%cpu，极少量内存
     * 7800 qps,主要集中在11%cpu，内存平稳
     * 10800 qps,12～15%cpu,内存平稳
     *
     * ConcurrentLinkedQueue：
     * 1860 qps,约3%cpu，极少量内存
     * 4700 qps,6.5～7%cpu，极少量内存
     * 8500 qps,10~11%cpu
     * 10700 qps,13~14%cpu，少量内存开销，几乎不用关心
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {

        QPExecutor e = new QPExecutor(new LinkedBlockingQueue());
//        QPExecutor e = new QPExecutor(new ArrayBlockingQueue(50000));
//        QPExecutor e = new QPExecutor(new ConcurrentLinkedQueue());
        e.runWithQps(1000);
        for (int i=0;i<10;i++){
            Thread.sleep(10000);
            e.watch();
        }
        e.stop();
    }



}
