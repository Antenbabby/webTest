package top.antennababy.demo.web.webtest.demos.service

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.collection.CollUtil
import cn.hutool.json.JSONUtil
import top.antennababy.demo.web.webtest.demos.web.User

class BeanCopyTest {
    static void main(String[] args) {
        def user = new User()
        user.age = 1
        def user1 = new User()
        user1.age = 2
        user1.name = "name"
        BeanUtil.copyProperties(user, user1)
        println(JSONUtil.toJsonStr(user1))

        println (["a","b"]*.concat("bb").each { println it})
        //直接调用属性
        println user.@age
        //调用getAge方法获取结果
        println user.age
    }
}
