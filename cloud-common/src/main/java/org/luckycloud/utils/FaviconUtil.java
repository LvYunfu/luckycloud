package org.luckycloud.utils;


import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
@Log4j2
public class FaviconUtil {

    /**
     * 获取网站Favicon图标地址
     * @param url 网站URL，可以不带协议（http/https）
     * @return Favicon图标地址，如果获取不到则返回默认的favicon.ico路径
     */
    public static String getFaviconUrl(String url) {
        String urlWithProtocol = addProtocolIfMissing(url);

        try {
            // 1. 解析HTML获取Favicon标签
            Document document = Jsoup.connect(urlWithProtocol).get();
            Elements faviconLinks = document.select("link[type='image/x-icon'], link[rel~='icon']");

            // 2. 优先使用type="image/x-icon"的标签
            if (!faviconLinks.isEmpty()) {
                String href = faviconLinks.get(0).attr("href");
                return resolveUrl(urlWithProtocol, href);
            }

            // 3. 如果找不到，尝试默认路径
            return resolveUrl(urlWithProtocol, "/favicon.ico");

        } catch (IOException e) {
            log.info("获取Favicon失败:{} " , e.getMessage());
            return resolveUrl(urlWithProtocol, "/favicon.ico");
        }
    }

    /**
     * 确保URL有协议（http/https）
     */
    private static String addProtocolIfMissing(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        return "https://" + url;
    }

    /**
     * 解析相对URL为绝对URL
     */
    private static String resolveUrl(String baseUrl, String href) {
        // 1. 如果已经是绝对URL，直接返回
        if (href.startsWith("http://") || href.startsWith("https://")) {
            return href;
        }

        // 2. 如果是根路径，直接拼接
        if (href.startsWith("/")) {
            try {
                URL base = new URL(baseUrl);
                return base.getProtocol() + "://" + base.getHost() + href;
            } catch (MalformedURLException e) {
                return baseUrl + href;
            }
        }

        // 3. 处理相对路径
        return baseUrl + (baseUrl.endsWith("/") ? "" : "/") + href;
    }
    

}
