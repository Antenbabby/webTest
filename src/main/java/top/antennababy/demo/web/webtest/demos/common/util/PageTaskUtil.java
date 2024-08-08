package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class PageTaskUtil {
    private final static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2+1);


    /**
     * 分页异步执行,生产者为分页主线程执行,消费者为异步线程池执行
     * @param count 生产者记录总数
     * @param pageSize 分页大小
     * @param getList 获取生产者数据,入参为 分页开始索引
     * @param callableFunction 消费数据
     * @return 执行任务的返回结果用,分割
     */
    public static <T, U> String exe(Long count, Integer pageSize, Function<Integer,List<T>> getList, Function<T,Callable<U>> callableFunction){
        try {
            TimeInterval timer = DateUtil.timer();
            ArrayList<U> failRecord = new ArrayList<>();
            for (int i = 0; (long) i *pageSize < count; i++) {
                //获取列表数据
                List<T> empNos = getList.apply(pageSize*i);
                List<Future<U>> futureList=new ArrayList<>();
                empNos.forEach(x->{
                    //处理数据
                    FutureTask<U> task = new FutureTask<>(callableFunction.apply(x));
                    futureList.add(task);
                    executorService.execute(task);
                });
                //获取处理数据的返回值
                for (Future<U> x : futureList) {
                    U taskRet = x.get();
                    if (taskRet!=null) {
                        failRecord.add(taskRet);
                    }
                }
                double percent = ((i+1.0)*pageSize>count?count:(i+1.0)*pageSize)/ count;
                log.info("PageTaskUtil.exe 已处理{}页,共{}页,进度{}%,已用时间:{}s,预计剩余时间{}s",i+1,Math.ceil(count*1.0/pageSize), String.format("%.2f", percent*100),timer.intervalSecond(),String.format("%.2f",timer.intervalSecond()/percent*(1-percent)));
            }
            String ret = "Done,but failed Ids:\n" + CollUtil.join(failRecord, ",");
            log.info("PageTaskUtil.exe 已完成!共处理{}条,用时{}s.\n{}",count,timer.intervalSecond(),ret);
            return ret;
        } catch (Exception e) {
            log.error("PageTaskUtil.exe error:",e);
            return e.getMessage();
        }
    }
    /**
     * 分页异步执行,生产者为分页主线程执行,消费者为异步线程池执行
     * @param count 生产者记录总数
     * @param pageSize 分页大小
     * @param getList 获取生产者数据,入参为 分页开始索引
     * @param process 消费数据
     * @param errorFun 出现异常时用返回数据
     * @return 返回出错的数据按,分割
     */
    public static <T, U> String exe(Long count, Integer pageSize, Function<Integer,List<T>> getList, Consumer<T> process, Function<T,U> errorFun){
        Function<T,Callable<U>> callableFunction=x->()->{
            try {
                process.accept(x);
                return null;
            } catch (Exception e) {
                log.error("PageTaskUtil.exe runnable error:",e);
                log.error("PageTaskUtil.exe current Record:\n{}", JSONUtil.toJsonStr(x));
                return errorFun.apply(x);
            }
        };
        return exe(count, pageSize, getList, callableFunction);
    }
    /**
     * 分页异步执行,生产者为分页主线程执行,消费者为异步线程池执行
     * @param count 生产者记录总数
     * @param pageSize 分页大小
     * @param getList 获取生产者数据,入参为 分页开始索引
     * @param process 消费数据
     */
    public static <T, U> void exe(Long count, Integer pageSize, Function<Integer,List<T>> getList, Consumer<T> process){
        Function<T,Callable<U>> callableFunction=x->()->{
            try {
                process.accept(x);
            } catch (Exception e) {
                log.error("PageTaskUtil.exe runnable error:",e);
                log.error("PageTaskUtil.exe current Record:\n{}", JSONUtil.toJsonStr(x));
            }
            return null;
        };
        exe(count, pageSize, getList, callableFunction);
    }
}
