package top.antennababy.demo.web.webtest.demos.groovy

import cn.hutool.core.util.StrUtil
import java.util.Scanner

class GetSql {
    /**
     * 通过日志拼接sql
     * @param args
     */
    static void main(String[] args) {
        println("请输入日志:")
        String sql, params;
        Scanner scanner = new Scanner(System.in)
        while (scanner.hasNextLine()) {
            def line = scanner.nextLine()
            if (line.contains('Preparing:')) {
                sql = line.substring(line.indexOf('Preparing:') + 11).trim().replace('?', '{}')
            } else if (line.contains('Parameters:')) {
                if (sql == null) {
                    continue
                }
                params = line.substring(line.indexOf('Parameters:') + 11).trim()
                params.split(',').collect {"${it.trim()}"}.each { println it }
                def collect = params.split(',').collect {"${it.trim()}"}.collect { "'${StrUtil.subBefore(it, "(",false)}'" }
                println "拼接后的sql:"
                println(StrUtil.format(sql, collect.toArray()))
                sql= null
            } else if (line.trim() == 'exit'){
                return;
            }
        }
    }
}
