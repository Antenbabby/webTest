package top.antennababy.demo.web.webtest.demos.groovy;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.HashMap;
@Slf4j
public class BotChat {
//    public static void main(String[] args) {
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("conversationId", "afssa");
//        objectObjectHashMap.put("customId", "1");
//        objectObjectHashMap.put("orgCode", "sf");
//        objectObjectHashMap.put("msgId", "aaa1");
//        objectObjectHashMap.put("role", "1");
//        objectObjectHashMap.put("content", "你好");
//        System.out.println(JSONUtil.toJsonStr(objectObjectHashMap));
//        String post = HttpUtil.post("https://gate-dev.op.laikang.com/aihealthmanager-dev/alg-api/talk", JSONUtil.toJsonStr(objectObjectHashMap));
//        System.out.println(post);
//    }
public static void main(String[] args) throws IOException {
//    AlgTalkDto param = new AlgTalkDto("afssa", "1",  "aaa3", "sf", "1","你好",null,null);
//    ThreadUtil.execAsync(()->{
//        HttpURLConnection con = null;
//        try {
////            con = SSEClient.createSSE("https://gate-dev.op.laikang.com/aihealthmanager-dev"+"/alg-api/talk", param);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (con != null){
//            //读取返回值
//            try {
//                SSEClient.readStream(con.getInputStream(), x -> {
//                    log.info("DialogServiceImpl.conventionHandler 算法响应参数参数：{}", JSONUtil.toJsonStr(x));
//                    ChatResponse bean = JSONUtil.toBean(x.getData(), ChatResponse.class);
//                });
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    });
}
}
