package com.piaoniu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/6/8
 *         Time: 上午9:10
 */
public abstract class TextUtils {

	public static String summaryChinese(String raw, int max) {
		if (raw.length() <= max) {
			return raw;
		} else {
			return StringUtils.substring(raw, 0, max) + "...";
		}
	}
	public static String subString(String raw, int max) {
		if (raw.length() <= max) {
			return raw;
		} else {
			return StringUtils.substring(raw, 0, max);
		}
	}
	public static String replaceAllBracket(String raw){
		String regex = "<.*?>";
		return raw.replaceAll(regex,"");
	}

	public static void main (String args[]){
		String source = "<div class=\"para\">\n" +
				"<div class=\"para\"><span style=\"color:#000000\">TFBOYS是</span><a href=\"http://www.baike.com/wiki/%E5%8C%97%E4%BA%AC%E6%97%B6%E4%BB%A3%E5%B3%B0%E5%B3%BB%E6%96%87%E5%8C%96%E8%89%BA%E6%9C%AF%E5%8F%91%E5%B1%95%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8\" target=\"_blank\"><span style=\"color:#000000\">北京时代峰峻文化艺术发展有限公司</span></a><span style=\"color:#000000\">于2013年推出的javascript:void(&#39;自动&#39;)组合，由</span><a href=\"http://www.baike.com/wiki/%E7%8E%8B%E4%BF%8A%E5%87%AF\" target=\"_blank\"><span style=\"color:#000000\">王俊凯</span></a><span style=\"color:#000000\">、</span><a href=\"http://www.baike.com/wiki/%E7%8E%8B%E6%BA%90\" target=\"_blank\"><span style=\"color:#000000\">王源</span></a><span style=\"color:#000000\">和</span><a href=\"http://www.baike.com/wiki/%E6%98%93%E7%83%8A%E5%8D%83%E7%8E%BA\" target=\"_blank\"><span style=\"color:#000000\">易烊千玺</span></a><span style=\"color:#000000\">3名成员组成。2013年10月18日，TFBOYS发行首张EP《Heart梦&middot;出发》。2014年3月，TFBOYS发行单曲</span><a href=\"http://www.baike.com/wiki/%E3%80%8A%E9%AD%94%E6%B3%95%E5%9F%8E%E5%A0%A1%E3%80%8B\" target=\"_blank\"><span style=\"color:#000000\">《魔法城堡》</span></a><span style=\"color:#000000\">；7月9日，TFBOYS被任命为央视大型励志少年才智秀</span><a href=\"http://www.baike.com/wiki/%E3%80%8A%E5%B0%91%E5%B9%B4%E4%B8%AD%E5%9B%BD%E5%BC%BA%E3%80%8B\" target=\"_blank\"><span style=\"color:#000000\">《少年中国强》</span></a><span style=\"color:#000000\">代言人；10月17日，担任芒果V基金举办公益活动&ldquo;圆梦计划&rdquo;的&ldquo;圆梦大使&rdquo;；11月17日，TFBOYS发布专辑</span><a href=\"http://www.baike.com/wiki/%E3%80%8A%E9%9D%92%E6%98%A5%E4%BF%AE%E7%82%BC%E6%89%8B%E5%86%8C%E3%80%8B\" target=\"_blank\"><span style=\"color:#000000\">《青春修炼手册》</span></a><span style=\"color:#000000\">。2015年12月11日，组合体TFBOYS《大梦想家》MV正式上线，其同名首张MINI专辑《大梦想家》于18日台湾大陆同步上线。2016年1月，TFBOYS加盟《最强大脑第三季》。2016年2月7日，TFBOYS登上中央电视台春节联欢晚会。2016年5月12日，央视&ldquo;CCTV看点&rdquo;官微宣布2016央视</span><a href=\"http://www.baike.com/wiki/%E5%85%AD%E4%B8%80%E6%99%9A%E4%BC%9A\" target=\"_blank\"><span style=\"color:#000000\">六一晚会</span></a><span style=\"color:#000000\">正式启动，TFBOYS组合将会在</span><a href=\"http://www.baike.com/wiki/%E5%84%BF%E7%AB%A5%E8%8A%82\" target=\"_blank\"><span style=\"color:#000000\">儿童节</span></a><span style=\"color:#000000\">登台献唱。</span></div>\n" +
				"</div>\n";
		Pattern pattern = Pattern.compile("<.*?>");
		Matcher matcher = pattern.matcher(source);
		matcher.find();
		System.out.print(replaceAllBracket(source));
	}


}
