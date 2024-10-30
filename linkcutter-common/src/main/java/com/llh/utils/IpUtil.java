package com.llh.utils;


import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = null;

        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {
            String ips = request.getHeader(header);
            if (ips != null && ips.length() != 0 && !"unknown".equalsIgnoreCase(ips)) {
                // 处理有逗号的情况，取第一个非 unknown 的有效 IP
                String[] ipArray = ips.split(",");
                for (String potentialIp : ipArray) {
                    potentialIp = potentialIp.trim();
                    if (!"unknown".equalsIgnoreCase(potentialIp)) {
                        ip = potentialIp;
                        break;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 处理 IPv6 本地地址
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }
}
