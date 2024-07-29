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

@LiteflowComponent("b")
public class BCmp extends NodeComponent {

    @Override
    public void process() {
        System.out.println("BCmp executed!");
        Object cmpData = this.getCmpData(Object.class);
        JSONObject firstContextBean = this.getFirstContextBean();
        System.out.println(JSONUtil.toJsonStr(firstContextBean));
        firstContextBean.set("fasdfab", "fasdfasdf");
        firstContextBean.set("fadfa2212", "sfaf2222");
        System.out.println(this.getRequestData().toString());
        System.out.println(JSONUtil.toJsonStr(cmpData));
        System.out.println(JSONUtil.toJsonStr(firstContextBean));
    }

}
