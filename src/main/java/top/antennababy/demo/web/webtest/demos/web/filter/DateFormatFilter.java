package top.antennababy.demo.web.webtest.demos.web.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class DateFormatFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法，可以在这里做一些初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 包装请求和响应
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);

        // 继续处理请求
        chain.doFilter(requestWrapper, responseWrapper);

        // 获取请求字段
        String dateFormat = getParam(requestWrapper);
        System.out.println(" dateFormat:"+dateFormat);

        if (StrUtil.isNotEmpty(dateFormat)) {
            // 获取响应内容
            String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            // 修改响应参数
            String modifiedResponseBody = modifyResponseBody(responseBody);
            // 将修改后的响应内容写回客户端
            responseWrapper.resetBuffer();
            responseWrapper.getWriter().write(modifiedResponseBody);
        }
        responseWrapper.copyBodyToResponse();
    }

    private static String getParam(ContentCachingRequestWrapper requestWrapper) {
        String requestBody = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        String dateFormat = requestWrapper.getParameter("dateFormat");
        if (StrUtil.isEmpty(dateFormat)&&StrUtil.isNotEmpty(requestBody)){
            JSONObject entries = JSONUtil.parseObj(requestBody);
            dateFormat =entries.getStr("dateFormat");
        }
        return dateFormat;
    }

    @Override
    public void destroy() {
        // 销毁方法，可以在这里做一些清理操作
    }
    // 修改响应体的方法
    private String modifyResponseBody(String responseBody) {
        JSONObject entries = JSONUtil.parseObj(responseBody);
        entries.set("dateFormat","yyyy-MM-dd HH:mm:ss");
        return entries.toString();
    }
}
