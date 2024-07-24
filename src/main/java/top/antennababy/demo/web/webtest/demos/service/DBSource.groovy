package top.antennababy.demo.web.webtest.demos.service

import cn.hutool.setting.dialect.Props
import groovy.sql.Sql
import org.springframework.stereotype.Component

class DBSource {
    static Sql sql;
    static  getSql(){
        if(sql==null){
            def props = new Props("application.properties")
            sql = Sql.newInstance(props.get("spring.datasource.url"),
                    props.get("spring.datasource.username"),
                    props.get("spring.datasource.password"),
                    props.get("spring.datasource.driver-class-name"))
        }
        return sql;
    }
    static firstRow(String sql){
        return getSql().firstRow(sql)
    }
    static rows(String sql){
        return getSql().rows(sql)
    }
}
