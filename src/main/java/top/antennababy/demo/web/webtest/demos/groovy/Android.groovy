package top.antennababy.demo.web.webtest.demos.groovy

import cn.hutool.json.JSONUtil
import groovy.util.logging.Slf4j;

@Slf4j
class Android {
    private int mCompileSdkVersion
    private String mBuildToolsVersion
    private ProductFlavor mProductFlavor

    Android() {
        this.mProductFlavor = new ProductFlavor()
    }

    void compileSdkVersion(int compileSdkVersion) {
        this.mCompileSdkVersion = compileSdkVersion
    }

    void buildToolsVersion(String buildToolsVersion) {
        this.mBuildToolsVersion = buildToolsVersion
    }

    void defaultConfig(Closure closure) {
        closure.delegate=mProductFlavor
        closure.setResolveStrategy Closure.DELEGATE_FIRST
        closure()
    }
    class ProductFlavor {
        private int mVersionCode
        private String mVersionName
        private int mMinSdkVersion
        private int mTargetSdkVersion

        def versionCode(int versionCode) {
            mVersionCode = versionCode
        }

        def versionName(String versionName) {
            mVersionName = versionName
        }

        def minSdkVersion(int minSdkVersion) {
            mMinSdkVersion = minSdkVersion
        }


        def targetSdkVersion(int targetSdkVersion) {
            mTargetSdkVersion = targetSdkVersion
        }

        @Override
        String toString() {
            return "ProductFlavor{" +
                    "mVersionCode=" + mVersionCode +
                    ", mVersionName='" + mVersionName + '\'' +
                    ", mMinSdkVersion=" + mMinSdkVersion +
                    ", mTargetSdkVersion=" + mTargetSdkVersion +
                    '}'
        }
    }
    @Override
    String toString() {
        return "Android{" +
                "mCompileSdkVersion=" + mCompileSdkVersion +
                ", mBuildToolsVersion='" + mBuildToolsVersion + '\'' +
                ", mProductFlavor=" + mProductFlavor +
                '}'
    }

    static void main(String[] args) {
        //闭包定义
        def android = {
            compileSdkVersion (25)
            buildToolsVersion ("25.0.2")
            defaultConfig {
                minSdkVersion 15
                targetSdkVersion 25
                versionCode 1
                versionName "1.0"
            }
        }
        //调用
        Android bean = new Android()
        android.delegate = bean
        android.call();
        println(bean.toString())
        log.info bean.toString()
        println(JSONUtil.toJsonStr(bean))
    }
}

