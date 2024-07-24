package top.antennababy.demo.web.webtest.demos.service

import cn.hutool.core.bean.BeanUtil
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
    }
}
