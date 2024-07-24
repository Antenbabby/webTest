package top.antennababy.demo.web.webtest.demos.service;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.json.JSONUtil;

public class BloomTest {
    public static void main(String[] args) {
        BitMapBloomFilter filter = new BitMapBloomFilter(10000);
        filter.add("1");
        filter.add("阿发懂法");

        System.out.println(JSONUtil.toJsonStr(filter));
    }
}
