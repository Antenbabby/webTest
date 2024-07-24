package top.antennababy.demo.web.webtest.demos.service

import cn.hutool.json.JSONUtil
import groovy.util.logging.Slf4j

@Slf4j
class SqlTest {
    static void main(String[] args) {
        def sql = DBSource.getSql()
//        sql.execute("create table if not exists CONFIG(id int primary key, name varchar(255), value varchar(255))")
//        sql.executeInsert("insert into CONFIG(id, name, value) values(1, 'name', 'value')")
//        sql.executeInsert("insert into CONFIG(id, name, value) values(2, 'name2', 'value2')")
        def result = sql.rows("select * from CONFIG where 1 = 1")
//        result.each {
//            println(JSONUtil.toJsonStr(it))
//        }
//        result.findAll{it.get("id")>1}.each{println(JSONUtil.toJsonStr(it))}
        def entries = result.findAll{it.id>1}.collectEntries({[(it.get("id")):it]})
        log.info(JSONUtil.toJsonStr(entries))
    }
}
