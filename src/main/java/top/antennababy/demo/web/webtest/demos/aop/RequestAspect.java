package top.antennababy.demo.web.webtest.demos.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import top.antennababy.demo.web.webtest.demos.dto.Result;

import java.util.Arrays;

/**
 * 日志切面
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logPoint() {
    }

    @Around("logPoint()")
    public Object  log(ProceedingJoinPoint pjp) throws Throwable {
        String trackId= IdUtil.fastSimpleUUID();
        log.info("trackId:{}",trackId);
        //获取该注解的实例对象
        Signature signature = pjp.getSignature();
        RequestMapping annotation = ((MethodSignature) signature).
                getMethod().getAnnotation(RequestMapping.class);
        String logPrefix = annotation.name();
        if (StrUtil.isBlank(logPrefix)) {
            String className=pjp.getTarget().getClass().getSimpleName().startsWith("$")
                    ? Arrays.stream(pjp.getTarget().getClass().getInterfaces()).findFirst().orElse(Object.class).getSimpleName()
                    :pjp.getTarget().getClass().getSimpleName();
            logPrefix = className + ":" + signature.getName();
        }
        Object proceed;
        TimeInterval timer = DateUtil.timer();
        try {
            try {
                //打印执行前日志
                Object[] args = pjp.getArgs();
                StringBuilder preFixLog = new StringBuilder(logPrefix);
                String[] parameterNames = ((MethodSignature) signature).getParameterNames();
                if(args!=null&&args.length>0){
                    for (int i = 0; i < args.length; i++) {
                        if(args[i]==null||ClassUtils.isPrimitiveOrWrapper(args[i].getClass()) || args[i] instanceof String){
                            preFixLog.append(" ").append(parameterNames!=null?parameterNames[i]:("args"+i)).append(": ").append( args[i]);
                        }else {
                            preFixLog.append(" ").append(parameterNames!=null?parameterNames[i]:("args"+i)).append(": ").append(JSONUtil.toJsonStr(args[i]));
                        }
                    }
                }
                log.info(preFixLog.toString());
            } catch (Exception e) {
                log.error("LogAspect切面出错.",e);
            }
            //执行方法
            proceed = pjp.proceed();
        } catch (Exception e) {
            log.error(logPrefix+"trackId: "+trackId+" costTime:"+timer.interval()+" error: ", e);
            throw e;
        }
        //添加trackId
        if(proceed instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) proceed;
            jsonObject.set("trackId", trackId);
        }else if (proceed instanceof Result) {
            Result<?> result = (Result<?>) proceed;
            result.setTrackId(trackId);
        }
        //打印执后前日志
        log.info(logPrefix+" costTime:{} trackId:{} result: {}",timer.interval(),trackId, JSONUtil.toJsonStr(proceed));
        return proceed;
    }
}
