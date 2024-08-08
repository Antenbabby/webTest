package top.antennababy.demo.web.webtest.demos.common.cow;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class File {
    @Getter
    private String name;
    private StringBuilder content;

    public File(String name, String content) {
        this.name = name;
        this.content = new StringBuilder(content);
    }

    public String getContent() {
        return content.toString();
    }

    public void setContent(String content) {
        this.content = new StringBuilder(content);
    }

    public void appendContent(String additionalContent) {
        this.content.append(additionalContent);
    }
}
