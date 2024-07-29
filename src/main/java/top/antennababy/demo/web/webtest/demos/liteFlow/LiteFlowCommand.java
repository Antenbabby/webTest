package top.antennababy.demo.web.webtest.demos.liteFlow;

import cn.hutool.json.JSONObject;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.enums.ScriptTypeEnum;
import com.yomahub.liteflow.flow.FlowBus;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LiteFlowCommand implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public void run(String... args) throws Exception {
        FlowBus.addScriptNode("s1", "s1", NodeTypeEnum.SCRIPT,"println(\"s1 executed!\")", ScriptTypeEnum.GROOVY.getEngineName());
        LiteFlowChainELBuilder.createChain().setChainId("chain1").setEL(
                "THEN(a,b,s1,c)"
        ).build();

        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "afadsfadsfaf", JSONObject.class);
        if (response.isSuccess()){
            log.info("执行成功");
        }else{
            log.info("执行失败");
        }
    }
}
