package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

public class ChatStreamUtil {
    public static void post(String url, Object param, LineHandler opt) {
        HttpRequest request = HttpRequest.post(url).body(JSONUtil.toJsonStr(param));
        HttpResponse execute = request.execute(true);
        LineHandler lineHandler = l -> {
            if (StrUtil.isNotEmpty(l)) {
                opt.handle(l);
            }
        };
        IoUtil.readUtf8Lines(execute.bodyStream(), lineHandler);
    }
}
