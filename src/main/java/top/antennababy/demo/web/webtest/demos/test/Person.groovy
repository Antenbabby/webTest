package top.antennababy.demo.web.webtest.demos.test

import cn.hutool.json.JSONUtil

class Person {
    String name
    Integer age
    //操作符重载 +
    Person plus(Person p) {
        this.age += p.age
        this
    }

    static String describe(Person p) {
        //return 可以省略
        "$p.name is $p.age"
    }

    static def transform(List elements, Closure action) {                    // 注释 1
        def result = []
        elements.each {
            result << action(it)
        }
        result
    }

    static void main(String[] args) {
        println JSONUtil.toJsonStr(new Person(name: 'Bob', age: 42))
        //操作符重载
        def p1 = new Person(name: 'Bob', age: 42)
        def p2 = new Person(name: 'Julia', age: 35)
        def p3 = p1 + p2
        println "操作符重载:" + JSONUtil.toJsonStr(p3)
        //.& 运算符用于获取方法的引用(方法指针,直接把方法当做闭包传递,类似 java中的::)
        def action = this.&describe
        def list = [new Person(name: 'Bob', age: 42),
                    new Person(name: 'Julia', age: 35)]                           // 注释 4
        assert transform(list, action) == ['Bob is 42', 'Julia is 35']

        def foo = Integer::new
        def fortyTwo = foo('42')
        println fortyTwo.getClass().getName()

        def abc;
        //Elvis 猫王运算符 ?= 用于判断左边的变量是否为null，如果为null则赋值，否则不赋值
        abc ?= "aaa";
        println("Elvis": abc)
        //安全导航操作符 ?. 用于简化对null对象的访问
        Person person;
        println("person未初始化也不会报错," + person?.age)
        //范围操作符
        def range = 1..10
        println range.getClass().getName()
        println range.each { print(it) }
        //8.3 宇宙飞船操作符

    }
}
