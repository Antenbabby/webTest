package top.antennababy.demo.web.webtest.demos.netcut;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 文本派存储工具
 * 用于存储一些简单的数据
 * key-value形式
 * <a href="https://txtpad.cn/">https://txtpad.cn/</a>
 */
public class TxtPadStorageUtil {
    //apiurl
    private static final String URL = "https://api.txttool.cn";
    //站点路径
    private static final String NOTE_NAME= "lxygwqf";//落霞与孤鹜齐飞
    //站点密码
    private static final String PSW = "lxygwqf";
    //内容密码
    private static final String CONTENT_PSW = "8a949b6843d3474f83f8bee5b143db96";
    //缓存开关
    private static final boolean cacheSwitch = true;


    //缓存
    static TimedCache<String, JSONObject> timedCache = CacheUtil.newTimedCache(60*1000);

    private static JSONObject storgeInfo = new JSONObject();

    //自动清理过期缓存,注释掉后将在下次调用时清理
    static {
//        timedCache.schedulePrune(5);
    }

    /**
     * 存储数据 key-value
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        Assert.notNull(key);
        JSONObject data = getAll();
        data.set(key, value);
        save(data);
    }

    /**
     * 保存/更新数据
     * @param data
     */
    private static void save(JSONObject data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("txt_name", NOTE_NAME);
        map.put("password", PSW);
        String encode = Base64.encode(SecureUtil.aes(CONTENT_PSW.getBytes(StandardCharsets.UTF_8)).encrypt(JSONUtil.toJsonStr(data).getBytes(StandardCharsets.UTF_8)));
        map.put("txt_content", JSONUtil.toJsonStr(CollUtil.newArrayList(Dict.of("title",StrUtil.sub(encode,0,20),"content",encode))));
        map.put("v_id", storgeInfo.get("v_id"));
        String post = HttpUtil.post(URL + "/txtpad/txt/save/", map);
        JSONObject ret = JSONUtil.parseObj(post);
        //成功
        if (ObjUtil.equals( ret.get("status"), 1)) {
            storgeInfo.set("v_id",ret.getJSONObject("data").getStr("v_id"));
            System.out.println("NetCutStorageUtil:保存成功");
        } else {
            throw new RuntimeException("NetCutStorageUtil:保存失败");
        }
    }

    /**
     * 获取数据 key-value
     * @param key
     * @return
     */
    public static String get(String key) {
        Assert.notNull(key);
        String str = getAll().getStr(key);
        System.out.println(StrUtil.format("NetCutStorageUtil:getRet: {}:{}",key,str));
        return str;
    }

    /**
     * 获取所有数据
     * @return
     */
    public static JSONObject getAll() {
        if (cacheSwitch) {
            JSONObject cacheObj = timedCache.get("all",false);
            if (cacheObj !=null) {
                System.out.println("NetCutStorageUtil:缓存命中");
                return cacheObj;
            }
        }
        String post = HttpUtil.post(URL + "/txtpad/txt/detail/", Dict.of("txt_name", NOTE_NAME,"password",PSW));
        JSONObject ret = JSONUtil.parseObj(post);
        //成功
        if (ObjUtil.equals( ret.get("status"), 1)) {
            storgeInfo.set("v_id",ret.getJSONObject("data").getStr("v_id"));
            String txtContents = ret.getJSONObject("data").getStr("txt_content");
            JSONArray objects = JSONUtil.parseArray(txtContents);
            JSONObject entries = (JSONObject) objects.get(0);
            JSONObject retObj = JSONUtil.parseObj(Opt.ofBlankAble(decryptStr(entries.getStr("content"))).orElse("{}"));
            timedCache.put("all",retObj);
            return retObj;
        } else {
            storgeInfo=ret.getJSONObject("data");
            return new JSONObject();
        }
    }

    /**
     * 解密字符串
     * @param encodeStr
     * @return
     */
    private static String decryptStr(String encodeStr) {
      return   new String(SecureUtil.aes(CONTENT_PSW.getBytes(StandardCharsets.UTF_8)).decrypt(Base64.decode(encodeStr)));
    }

    /**
     * 删除数据 key-value
     * @param key
     */
    public static void delete(String key) {
        Assert.notNull(key);
        JSONObject data = getAll();
        data.remove(key);
        save(data);
    }

    public static void main(String[] args) {
        put("test", "test");
        System.out.println(get("test"));
    }
}
