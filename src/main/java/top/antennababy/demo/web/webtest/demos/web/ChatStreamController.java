package top.antennababy.demo.web.webtest.demos.web;

import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.antennababy.demo.web.webtest.demos.common.util.ChatStreamUtil;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatStreamController {

    @RequestMapping("/sse")
    public SseEmitter chat(String message) {
        log.info("message: {}", message);
        SseEmitter sseEmitter = new SseEmitter();
        new Thread(() -> {
            try {
                for (String s : Stream.of("hello", "world", "spring", "boot", "sse", "emitter")
                        .collect(Collectors.toSet())) {
                    try {
                        sseEmitter.send(s);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        log.error("error: {}", e.getMessage());
                    }
                }
                sseEmitter.complete();
            } catch (Exception e) {
                sseEmitter.completeWithError(e);
            }
        }).start();
        return sseEmitter;
    }
    @RequestMapping("/chatClient")
    public String chatClient(@RequestBody String message) throws IOException {
//        String post = HttpUtil.post("http://localhost:3001/chat/sse", Dict.of("message", message));
        String urlStr="http://localhost:3001/chat/sse";
        Dict param = Dict.of("message", message);
        LineHandler lineHandler = System.out::println;
        ChatStreamUtil.post(urlStr, param, lineHandler);

        log.info("message: {}", message);
        return message;
    }
}
