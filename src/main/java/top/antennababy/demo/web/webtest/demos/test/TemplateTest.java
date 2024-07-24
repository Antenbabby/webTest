package top.antennababy.demo.web.webtest.demos.test;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class TemplateTest {
    public static void main(String[] args) {
        String templateStr = "${baseUserInfo.userNick}，${baseUserInfo.genderDesc}，<#if baseUserInfo.age??>${baseUserInfo.age}岁，</#if>${warningDatas[0].name}${warningDatas[0].value}${warningDatas[0].unit}，指标异常。请及时处理.";
        String paramJson = "{\"deviceType\":\"WEIGHT\",\"warningMap\":{\"XYZBXY001013\":{\"vitalSignsSn\":28447,\"userId\":21411,\"itemType\":\"DailyMonitor\",\"itemTypeName\":\"日常监测\",\"itemCode\":\"010001\",\"itemName\":\"身高\",\"itemValue\":\"178.5\",\"itemValueUnit\":\"cm\",\"examTime\":1721108980719,\"createBy\":\"21411\",\"createTime\":1721108980719,\"updateTime\":1721108980724,\"deleteFlag\":\"0\",\"baseItemCode\":\"XYZBXY001013\",\"dailyMonitorId\":\"2102\",\"dataSource\":1},\"XYZBXY001001\":{\"vitalSignsSn\":28449,\"userId\":21411,\"itemType\":\"DailyMonitor\",\"itemTypeName\":\"日常监测\",\"itemCode\":\"XYZBXY001001\",\"itemName\":\"BMI\",\"itemValue\":\"17.8\",\"examTime\":1721108980719,\"createBy\":\"21411\",\"createTime\":1721108980719,\"updateTime\":1721108980725,\"deleteFlag\":\"0\",\"baseItemCode\":\"XYZBXY001001\",\"dailyMonitorId\":\"2102\",\"dataSource\":1},\"XYZBXY001007\":{\"vitalSignsSn\":28448,\"userId\":21411,\"itemType\":\"DailyMonitor\",\"itemTypeName\":\"日常监测\",\"itemCode\":\"010002\",\"itemName\":\"体重\",\"itemValue\":\"56.8\",\"itemValueUnit\":\"kg\",\"examTime\":1721108980719,\"createBy\":\"21411\",\"createTime\":1721108980719,\"updateTime\":1721108980725,\"deleteFlag\":\"0\",\"baseItemCode\":\"XYZBXY001007\",\"dailyMonitorId\":\"2102\",\"dataSource\":1}},\"warningDatas\":[{\"code\":\"XYZBXY001001\",\"name\":\"BMI\\r\\n\",\"value\":\"17.8\",\"unit\":\"d\",\"referenceValue\":\"23.1~23.5\"}],\"baseUserInfo\":{\"userId\":21411,\"userNick\":\"18600898183\",\"headUrl\":\"https://lk-shuzhizhongtai-common.oss-cn-beijing.aliyuncs.com/ai-laikang-com/IntelligentFiveTeachersWx/fiveteachers/boy%403x.png\",\"mobile\":\"18600898183\",\"genderDesc\":\"未知\",\"onlineStatus\":\"Login\",\"userType\":1,\"weight\":\"56.8\",\"height\":\"178.5\",\"bmi\":\"17.8\"}}";
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());

        //假设我们引入的是Beetl引擎，则：
        Template template = engine.getTemplate(templateStr);
        //Dict本质上为Map，此处可用Map
        JSONObject entries = JSONUtil.parseObj(paramJson);
        String result = template.render(entries);
        System.out.println(result);
    }
}
