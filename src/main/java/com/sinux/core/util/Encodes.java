package com.sinux.core.util;
import org.apache.commons.lang3.StringEscapeUtils;

public class Encodes {
	/**
	 * Html 转码.
	 * @param  html String类型
	 * @return String对象
	 * @throws
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}
}
