package top.antennababy.demo.web.webtest.demos.groovy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogToSting2Json {
    public static void main(String[] args) throws Exception {
        String log = "THbGroup(id=null, imGroupId=hb_dev@group_tR9fb7wiubdifWWV, groupName=213, groupStatus=NORMAL, ownerUserId=62, ownerImUserId=hb_dev@62, groupSetting={\"joinTime\":1717430400000,\"leaveTime\":1718812800000,\"introduction\":\"22\",\"notification\":\"2\",\"groupPlanCode\":\"\",\"groupPlanName\":\"\",\"groupRemark\":\"ee\",\"image\":\"https://lk-hb-public.oss-cn-beijing.aliyuncs.com/d23dd45e-f0c4-4784-9bd5-d7fdabd38616\",\"enable\":true}, isDel=null, type=null, createTime=null, updateTime=null, deleteTime=null, createBy=null, updateBy=null, deleteBy=null, orgCode=null)";

        // Remove the class name and parentheses
        log = log.substring(log.indexOf('(') + 1, log.lastIndexOf(')'));

        // Split the string into key-value pairs
        String[] pairs = log.split(", ");

        // Create a map to hold the key-value pairs
        Map<String, Object> map = new HashMap<>();

        // Parse the key-value pairs and add them to the map
        Pattern pattern = Pattern.compile("(.*?)=(.*)");
        for (String pair : pairs) {
            Matcher matcher = pattern.matcher(pair);
            if (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                // If the value is a JSON object, parse it as a map
                if (value.startsWith("{") && value.endsWith("}")) {
                    value = value.substring(1, value.length() - 1);
                    String[] subPairs = value.split(",");
                    Map<String, Object> subMap = new HashMap<>();
                    for (String subPair : subPairs) {
                        Matcher subMatcher = pattern.matcher(subPair);
                        if (subMatcher.find()) {
                            String subKey = subMatcher.group(1);
                            String subValue = subMatcher.group(2);
                            subMap.put(subKey, subValue);
                        }
                    }
                    map.put(key, subMap);
                } else {
                    map.put(key, value);
                }
            }
        }

        // Convert the map to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        System.out.println(json);
    }
}
