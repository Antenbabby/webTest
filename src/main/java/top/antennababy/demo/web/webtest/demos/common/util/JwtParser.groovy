package top.antennababy.demo.web.webtest.demos.common.util

import cn.hutool.core.date.DateUtil
import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil

class JwtParser {
    static void main(String[] args) {
        def scanner = new Scanner(System.in)
        println("请输入jwt: ")
        def input = scanner.nextLine()
        def jwt = parseJwt(input)
        initTime(jwt)
        def jwtObj = JSONUtil.toJsonStr(jwt)
        println("token解析后的内容为: \n ${jwtObj}")
    }

    //解析jwt
    def static parseJwt(String jwt) {
        def parts = jwt.split("\\.")
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT. Expected 3 parts separated by dots.")
        }
        def payload = new String(Base64.getUrlDecoder().decode(parts[1]), "UTF-8")
        return JSONUtil.parseObj(payload)
    }

    static def initTime(jwt) {
        jwt.each { k, v ->
            if (v instanceof Number &&v > 5000000) {
                if (v<9999999999) {
                    def date = new Date(v * 1000L)
                    jwt.set(k, DateUtil.formatDateTime(date))
                }else if (v<9999999999999) {
                    def date = new Date(v)
                    jwt.set(k, DateUtil.formatDateTime(date))
                }
            }else if (v instanceof Map) {
                jwt.set(k, initTime(v))
            }
        }
        return jwt
    }
}
