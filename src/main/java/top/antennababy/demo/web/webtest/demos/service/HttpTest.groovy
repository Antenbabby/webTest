package top.antennababy.demo.web.webtest.demos.service

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpResponse
import cn.hutool.http.HttpUtil
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class HttpTest {
//    static void main(String[] args) {
//        def res2 = 'http://127.0.0.1:12345/api/desc'.toURL()
//        println(res2.getText())
//
//        def http = new HTTPBuilder('http://127.0.0.1:12345')
//        http.request(Method.GET, ContentType.TEXT) {
//            //设置url相关信息
//            uri.path='/api/desc'
////            uri.query=[a:'1',b:2]
//            //设置请求头信息
//            headers.'User-Agent' = 'Mozill/5.0'
//            //设置成功响应的处理闭包
//            response.success= {resp,reader->
//                println resp.status
//                println resp.statusLine.statusCode
//                println resp.headers.'content-length'
//                System.out << reader
//            }
//            //根据响应状态码分别指定处理闭包
//            response.'404' = { println 'not found' }
//            response.'403' = { println 'not found' }
//            response.'406' = { println 'not found' }
//        }
//    }
    static void main(String[] args) {
        def response = HttpRequest.post("https://mirror-test.v.laikang.com/api/mirror/login/loginByThird")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"version\": \"1.0\",\n" +
                        "  \"head\": {\n" +
                        "    \"bodytype\": \"flat\",\n" +
                        "    \"maxresults\": 10,\n" +
                        "    \"firstresult\": 0\n" +
                        "  },\n" +
                        "  \"body\": [\n" +
                        "    {\n" +
                        "      \"appcode\": \"nUVmja3M\",\n" +
                        "      \"orgcode\": \"lkdnyfq\",\n" +
                        "      \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvUm55ZzVVWjM0VTZqY3o1RHU2WW9YVUhqUVpnIiwicm9sZXMiOlsiUk9MRV9DVVNUT01FUiJdLCJ1c2VySW5mbyI6eyJpZCI6MTA3OTEsIm91dElkIjoiNDIzOCIsInVzZXJOYW1lIjoib1JueWc1VVozNFU2amN6NUR1NllvWFVIalFaZyIsInVzZXJOaWNrIjoi6KiA5oyC6ZKpIiwiaGVhZFVybCI6Imh0dHBzOi8vbGstc2h1emhpemhvbmd0YWktY29tbW9uLm9zcy1jbi1iZWlqaW5nLmFsaXl1bmNzLmNvbS9haS1sYWlrYW5nLWNvbS9JbnRlbGxpZ2VudEZpdmVUZWFjaGVyc1d4L2ZpdmV0ZWFjaGVycy9ib3klNDAzeC5wbmciLCJtb2JpbGUiOiIxODgxMTYwNTUzMSIsImdlbmRlciI6MSwiYmlydGhEYXkiOjE3MDQ2NDMyMDAwMDAsImNyZWF0ZVRpbWUiOjE2OTM4OTMwNDQwMDAsInVzZXJUeXBlIjoxLCJvcmdDb2RlIjpudWxsLCJpbVVzZXJJZCI6ImhiX2RldkAxMDc5MSIsInVwZGF0ZVRpbWUiOjE3MTYxODk1MDUwMDAsInJvbGVzIjpbIlJPTEVfQ1VTVE9NRVIiXX0sImlhdCI6MTcxNjE5MTM2MiwiZXhwIjoxODczODcxMzYyfQ.RDLuuTbu3w2ONUfdtYIY162sxfgQGOYC4cv5AW0vJMY\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}").execute();
        response.getCookies().each { println it }
    }
}
