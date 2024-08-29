package top.antennababy.demo.web.webtest.demos.groovy

import cn.hutool.core.io.FileUtil

class TestGroovy {
    static void main(String[] args) {

        //获取工程根目录
        FileUtil.loopFiles(System.getProperty("user.dir")+"/.vscode").each {
            println(it.getAbsolutePath())
        }
    }
}
