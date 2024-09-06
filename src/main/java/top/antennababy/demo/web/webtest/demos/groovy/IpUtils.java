package top.antennababy.demo.web.webtest.demos.groovy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class IpUtils {

	private static final String UNKNOWN = "unknown";
	private IpUtils() {
		super();
	}

	public static List<String> ipHeader= CollUtil.newArrayList("x-forwarded-for","Proxy-Client-IP","WL-Proxy-Client-IP","X-Real-IP");
	/**
	 * 获取客户端IP
	 *
	 * @param request 请求对象
	 * @return IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return UNKNOWN;
		}
		String ip="";
		for (String header : ipHeader) {
			ip = request.getHeader(header);
			if (!isUnknown(ip)) {
				break;
			}
		}
		if (isUnknown(ip)) {
			ip = request.getRemoteAddr();
		}

		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
	}

	/**
	 * 从多级反向代理中获得第一个非unknown IP地址
	 *
	 * @param ip 获得的IP地址
	 * @return 第一个非unknown IP地址
	 */
	public static String getMultistageReverseProxyIp(String ip) {
		// 多级反向代理检测
		if (ip != null && ip.contains(",")) {
			final String[] ips = ip.trim().split(",");
			for (String subIp : ips) {
				if (!isUnknown(subIp)) {
					ip = subIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * 检测给定字符串是否为未知，多用于检测HTTP请求相关
	 *
	 * @param checkString 被检测的字符串
	 * @return 是否未知
	 */
	public static boolean isUnknown(String checkString) {
		return StrUtil.isBlank(checkString) || UNKNOWN.equalsIgnoreCase(checkString);
	}

}
