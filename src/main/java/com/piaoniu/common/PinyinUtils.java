package com.piaoniu.common;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author code4crafter@gmail.com
 */
@Slf4j
public class PinyinUtils {

	public static String getPinYin(String inputString) {
		return getPinYin(inputString, "_", false);
	}

	public static String getPinYin(String inputString, String separator, boolean onlyFirstLetter) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

		char[] input = inputString.trim().toCharArray();
		StringBuilder output = new StringBuilder("");
		boolean noHanZi = true;

		try {
			for (char anInput : input) {
				if ('\u4E00' <= anInput && '\u9FA5' >= anInput) {
					if (!noHanZi) {
						output.append(separator);
					}
					noHanZi = false;
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(anInput, format);
					if (temp != null) {
						if (!onlyFirstLetter) {
							output.append(temp[0]);
						} else {
							if (temp[0].length() > 0) {
								output.append(temp[0].substring(0, 1));
							}
						}
					} else {
						log.info("convert error, raw: {}", anInput);
					}
				} else
					output.append(Character.toString(anInput));
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			log.error("convert pinyin error {}", inputString, e);
		}
		return output.toString();
	}

}
