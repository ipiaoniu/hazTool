package com.piaoniu.biz;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/6/7
 *         Time: 下午4:24
 */
public abstract class UrlUtils {

	public static String concatQueryParams(String url, Map<String, String[]> map) {
		StringBuilder sb = new StringBuilder(url);
		AtomicBoolean first = new AtomicBoolean(true);
		map.forEach((k, vs) -> {
			//do not append pageSize or pageIndex to query string
			for (String v : vs) {
				if (v == null || v.isEmpty())
					continue;
				if (first.get()){
					sb.append("?");
					first.set(false);
				} else {
					sb.append("&");
				}
				sb.append(k).append("=").append(v);
			}
		});
		return sb.toString();
	}

	public static String formatURL(String url, Map<String, String> map) {
		StringBuilder sb = new StringBuilder(url);
		AtomicBoolean first = new AtomicBoolean(true);
		map.forEach((k, v) -> {
			//do not append pageSize or pageIndex to query string
				if (first.get()){
					sb.append("?");
					first.set(false);
				} else {
					sb.append("&");
				}
				sb.append(k).append("=").append(v);
		});
		return sb.toString();
	}



}
