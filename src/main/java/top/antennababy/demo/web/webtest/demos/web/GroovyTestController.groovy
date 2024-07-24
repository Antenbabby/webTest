package top.antennababy.demo.web.webtest.demos.web

import cn.hutool.json.JSONUtil
import groovy.util.logging.Slf4j
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.antennababy.demo.web.webtest.demos.dto.Result
import top.antennababy.demo.web.webtest.demos.service.DBSource

import javax.annotation.Resource

@Slf4j
@RestController
@RequestMapping("/groovy")
class GroovyTestController {

    @Resource
    JdbcTemplate jdbcTemplate

    @RequestMapping("/test")
    def test(String limit) {
        def rows = DBSource.rows("select * from CONFIG limit ${limit:1}")
        log.info(JSONUtil.toJsonStr(rows))
        return rows
    }
    @RequestMapping("/test2")
    def test2(String limit) {
        def rows = jdbcTemplate.queryForList("select * from CONFIG limit ${limit}")
        rows.each { log.info(JSONUtil.toJsonStr(it)) }
        return Result.success(rows)
    }
}
