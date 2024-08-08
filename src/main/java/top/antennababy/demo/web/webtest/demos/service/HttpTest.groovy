package top.antennababy.demo.web.webtest.demos.service

import cn.hutool.core.io.IoUtil
import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpResponse
import cn.hutool.http.HttpUtil
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.RESTClient

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
        new RESTClient("http://localhost:3001/enum/get/enum").request(Method.POST,ContentType.JSON) {
            body = [configKeys: ['CertificateTypeEnum']]
            response.success = { resp, reader ->
                print( reader)
            }
        }
    }
}
