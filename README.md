# queperformance
测试java中queue的性能

### 测试指标
1. 高并发，特定吞吐量下cpu消耗情况
2. 生产及消费一定数量元素需要的时间
3. 内存及GC情况

### 测试参数
- 监控工具：jprofiler sample采样
- CPU：4核
- 并发线程数：100
- qps：2000/5000/8000/10000/12000
- 数据量：1000000

### 测试对象
LinkedBlockingQueue
ArrayBlockingQueue
ConcurrentLinkedQueue
Disruptor

### 结论
Disruptor 没有测
100个线程并发的情况下，linekdBlockingQeue的性能相对比较好
在单线程1000左右qps的情况下，ConcurrentLinkedQueue占用的cpu开销会更小一些