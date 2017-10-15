package com.piaoniu.common;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/1/14
 *         Time: 上午9:29
 */
public abstract class MobileNumberUtils {

	/**
	 * 银行卡位数从16到19位不等，从只保留头4位末四位，其余加*
	 * @param number
	 * @return
	 */
	public static String mask(String number){
		return number.substring(0,3)+"****"+number.substring(number.length()-4,number.length());
	}

	public static void main(String[] args) {
		System.out.println(mask("13700000000"));
	}
}
