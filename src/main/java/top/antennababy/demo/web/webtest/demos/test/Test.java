package top.antennababy.demo.web.webtest.demos.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.math3.stat.StatUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        //中位线
        Map<String,Double> midLineMap=new LinkedHashMap<>();
        midLineMap.put("07:13", 0.0);
        midLineMap.put("07:24", 0.0);
        midLineMap.put("07:25", 0.1);
        midLineMap.put("07:26", 0.0);
        midLineMap.put("07:37", 0.0);
        midLineMap.put("07:38", 0.0);
//        midLineMap.put("07:49", 0.0);
        midLineMap.put("07:51", 0.0);
        //拼接10分钟内连续的时段  07:13 - 09:22 ；13:52 - 14:31;

    }

    public static String getTimeDuration(Map<String,Double> data, Predicate<Double> predicate){
        List<Map.Entry<String, Double>> collect = data.entrySet().stream().filter(x->predicate.test(x.getValue())).collect(Collectors.toList());
        List<String> midLineStrList=new ArrayList<>();
        String start=null;
        String end=null;
        int i=0;
        BiFunction<String, String,String> concatStr = (x,y) -> Objects.equals(x,y) ? x : x + "-" + y;
        for (Map.Entry<String, Double> entry : collect) {
            if(start==null){
                start=entry.getKey();
                end=entry.getKey();
                if(i==collect.size()-1){
                    midLineStrList.add(concatStr.apply(start,end));
                }
            }else {
                if(DateUtil.between(DateUtil.parse(start, "HH:mm"),DateUtil.parse(entry.getKey(), "HH:mm"), DateUnit.MINUTE)<=10){
                    end=entry.getKey();
                    if(i==collect.size()-1){
                        midLineStrList.add(concatStr.apply(start,end));
                    }
                }else {
                    //如果不连续
                    midLineStrList.add(concatStr.apply(start,end));
                    start=entry.getKey();
                    end=entry.getKey();
                    if(i==collect.size()-1){
                        midLineStrList.add(concatStr.apply(start,end));
                    }
                }
            }
            i++;
        }
        return CollUtil.join(midLineStrList,";");
    }
}
