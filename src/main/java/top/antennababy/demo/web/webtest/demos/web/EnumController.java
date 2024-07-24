package top.antennababy.demo.web.webtest.demos.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.antennababy.demo.web.webtest.demos.dto.ConfigQuery;
import top.antennababy.demo.web.webtest.demos.dto.Result;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/enum")
public class EnumController {
    /**
     * 获取枚举
     * @param config
     * @return
     */
    @PostMapping("/get/enum")
    Result<HashMap<String, List<HashMap<Object, Object>>>> getEnum(@RequestBody ConfigQuery config) {
        log.info("ConfigController.getEnum configKey:{}", config);
        Result<HashMap<String, List<HashMap<Object, Object>>>> result;
        try {
            HashMap<String, List<HashMap<Object, Object>>> hashMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(config.getConfigKeys())) {
                for (String configKey : config.getConfigKeys()) {
                    Class<?> aClass = Class.forName("top.antennababy.demo.web.webtest.demos.common." + configKey);
                    Object[] objects = aClass.getEnumConstants();
                    List<Field> fields = Arrays.stream(aClass.getDeclaredFields()).filter(f -> (!Objects.equals(f.getType().getSimpleName(), configKey)) && (!Objects.equals(f.getName(), "$VALUES"))).collect(Collectors.toList());
                    List<HashMap<Object, Object>> maps = Arrays.stream(aClass.getDeclaredFields()).filter(f -> Objects.equals(f.getType().getSimpleName(), configKey)).map(x -> {
                        HashMap<Object, Object> o = new HashMap<>();
                        o.put("enumName", x.getName());
                        return o;
                    }).collect(Collectors.toList());
                    for (int i = 0; i < objects.length; i++) {
                        for (Field field : fields) {
                            maps.get(i).put(field.getName(), ReflectUtil.getFieldValue(objects[i], field));
                        }
                    }
                    hashMap.put(configKey, maps);
                    System.out.println(JSONUtil.toJsonStr(hashMap));
                }
            }
            result = Result.success(hashMap);
        } catch (Exception e) {
            log.error("ConfigController.getEnum error", e);
            result = Result.fail("Error", e.getMessage());
        }
        log.info("ConfigController.getEnum result:{}", JSONUtil.toJsonStr(result));
        return result;
    }
}
