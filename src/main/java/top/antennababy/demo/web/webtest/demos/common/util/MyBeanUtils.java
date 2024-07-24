package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.map.multi.RowKeyTable;
import cn.hutool.core.map.multi.Table;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.TreeBasedTable;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class MyBeanUtils {
    /**
     * 把source转为target
     * @param source source
     * @param target target
     * @param <T> 返回值类型
     * @return 返回值
     * @throws Exception newInstance可能会抛出的异常
     */
    public static <T> T mapToObj(Map source, Class<T> target) {
        try {
            Field[] fields = target.getDeclaredFields();
            T o = target.newInstance();
            for(Field field:fields){
                Object val;
                if((val=source.get(field.getName()))!=null){
                    field.setAccessible(true);
                    field.set(o,val);
                }
            }
            return o;
        } catch (Exception e) {
            log.error("mapToObj error",e);
        }
        return null;
    }


    /**
     * source填充至target,只填充source中不为null的字段
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static Object copySelective(Object source, Object target) {
        Assert.notNull(source, "source must not be null");
        Assert.isTrue(BeanUtil.isBean(source.getClass()), "source must be bean");
        target = target == null ? ReflectUtil.newInstance(source.getClass()) : target;
        for (Field field : ReflectUtil.getFields(source.getClass())) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                continue;
            }
            Object newValue = ReflectUtil.getFieldValue(source, field);
            try {
                if (newValue != null) {
                    if (!BeanUtil.isBean(newValue.getClass())) {
                        ReflectUtil.setFieldValue(target, field.getName(), newValue);
                    } else {
                        ReflectUtil.setFieldValue(target, field, copySelective(newValue, ReflectUtil.getFieldValue(target, field)));
                    }
                }
            } catch (Exception e) {
                log.info("copySelective error skip targetClass:{} field:{},value:{}",target.getClass().getSimpleName(), field.getName(), newValue);
            }
        }
        return target;
    }

    /**
     * 迭代解析json
     * @param str
     * @return
     */
    public static JSONObject parseJsonObj(String str) {
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        JSONObject ret = JSONUtil.parseObj(str);
        return parseJsonObj(ret);
    }

    private static JSONObject parseJsonObj(JSONObject jsonObject) {
        jsonObject.entrySet().forEach(x -> {
            if (x.getValue() instanceof String) {
                if ((StrUtil.startWith(x.getValue().toString(), "{") && StrUtil.endWith(x.getValue().toString(), "}"))) {
                    try {
                        x.setValue(parseJsonObj(x.getValue().toString()));
                    } catch (Exception e) {
                        log.error("解析错误");
                    }
                }else if (StrUtil.startWith(x.getValue().toString(), "[") && StrUtil.endWith(x.getValue().toString(), "]")){
                    x.setValue(JSONUtil.parseArray(x.getValue().toString()));
                }
            }else if (x.getValue() instanceof JSONObject) {
                parseJsonObj((JSONObject) x.getValue());
            }
        });
        return jsonObject;
    }

    /**
     * 更新json字符串
     * @param json
     * @param key
     * @param value
     * @return
     */
    public static String updateJsonStr(String json,String key,Object value) {
        json = StrUtil.isEmpty(json)? StrPool.EMPTY_JSON:json;
        JSONObject jsonObject = JSONUtil.parseObj(json);
        jsonObject.set(key,value);
        return jsonObject.toString();
    }
    /**
     * 更新json字符串
     * @return
     */
    public static String  updateJsonStrByJson(String source,String target) {
        JSONObject param = JSONUtil.parseObj(Opt.ofNullable(source).orElse(StrPool.EMPTY_JSON));
        return updateJsonStrByMap(target,param);
    }
    /**
     * 更新json字符串
     * @param json
     * @return
     */
    public static String updateJsonStrByMap(String json,Map<? extends String,?> param) {
        json = StrUtil.isEmpty(json)? StrPool.EMPTY_JSON:json;
        JSONObject jsonObject = JSONUtil.parseObj(json);
        param= Opt.ofNullable(param).orElse(new HashMap<>());
        jsonObject.putAll(param);
        return jsonObject.toString();
    }
    /**
     * 过滤html内容,防止xss攻击
     * @param obj
     * @return
     * @param <T>
     */
    public static<T> T filterObjHtml(T obj) {
        try {
            //obj为字符串
            if (obj instanceof String) {
                return (T) HtmlUtil.filter((String) obj);
            }
            //obj为Bean
            if (obj instanceof Map) {
                Map<String, Object> map=(Map<String, Object>) obj;
                for (String key : map.keySet()) {
                    Object val = map.get(key);
                    if (val==null) {
                        continue;
                    }
                    if (map.get(key) instanceof String) {
                        map.put(key, HtmlUtil.filter(val.toString()));
                    } else if (BeanUtil.isBean(val.getClass())){
                        map.put(key, filterObjHtml(val));
                    }
                }
            }else if (obj instanceof Iterable){
                Iterable collection = (Iterable) obj;
                if (CollUtil.isNotEmpty(collection)) {
                    for (Object item : collection) {
                        if (item instanceof String) {
                            item = HtmlUtil.filter((String) item);
                        } else if (BeanUtil.isBean(item.getClass())) {
                            item = filterObjHtml(item);
                        }
                    }
                }
            }else if (BeanUtil.isBean(obj.getClass())) {
                System.out.println(JSONUtil.toJsonStr(obj));
                Map<String, Object> map = BeanUtil.beanToMap(obj);
                obj = (T) BeanUtil.mapToBean(filterObjHtml(map), obj.getClass(), true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return obj;
    }

    public static void main(String[] args) {
        Table<String,String,String> rowKeyTable = new RowKeyTable<>(new LinkedHashMap<>(), LinkedHashMap::new);
        rowKeyTable.put("2","1","3");
        rowKeyTable.put("1","2","2");
        rowKeyTable.put("1","1","1");
        System.out.println(JSONUtil.toJsonStr(rowKeyTable.rowMap()));
    }
}
