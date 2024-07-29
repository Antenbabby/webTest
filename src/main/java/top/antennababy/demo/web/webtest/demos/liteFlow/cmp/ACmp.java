/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package top.antennababy.demo.web.webtest.demos.liteFlow.cmp;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

@LiteflowComponent("a")
public class ACmp extends NodeComponent {

    @Override
    public void process() {
        System.out.println("ACmp executed!");
        JSONUtil.toJsonStr("ACmp executed!");
        Object requestData = this.getRequestData();
        JSONObject firstContextBean = this.getFirstContextBean();
        firstContextBean.set("fasdfab", "fasdfasdf");
        firstContextBean.set("fadfa2212", "sfaf2222");
        System.out.println(JSONUtil.toJsonStr(requestData));
        System.out.println(JSONUtil.toJsonStr(firstContextBean));
    }
}
