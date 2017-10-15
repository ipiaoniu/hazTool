package com.piaoniu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/4/6
 *         Time: 下午3:30
 */
@Slf4j
@Data
public class ClientVersion implements Comparable<ClientVersion> {

	private int major;

	private int minor;

	private int patch;

	private String label;

	private static Pattern p4version = Pattern.compile("(\\d+)\\.?(\\d+)?\\.?(\\d+)?[\\-_]?([^\\s]*)");

	public static ClientVersion from(String versionStr) {
		Matcher matcher = p4version.matcher(versionStr);
		if (!matcher.find()) {
			throw new IllegalArgumentException("invalid version " + versionStr);
		}
		ClientVersion clientVersion = new ClientVersion();
		clientVersion.setMajor(safeParseToInt(matcher.group(1)));
		clientVersion.setMinor(safeParseToInt(matcher.group(2)));
		clientVersion.setPatch(safeParseToInt(matcher.group(3)));
		clientVersion.setLabel(matcher.group(4));
		return clientVersion;
	}

	private static int safeParseToInt(String number) {
		if (number == null) {
			return 0;
		}
		return Integer.parseInt(number);
	}

	@Override
	public int compareTo(ClientVersion o) {
		if (o == null) {
			return 1;
		}
		int result = NumberUtils.compare(this.major, o.getMajor());
		if (result != 0) {
			return result;
		}
		result = NumberUtils.compare(this.minor, o.getMinor());
		if (result != 0) {
			return result;
		}
		result = NumberUtils.compare(this.patch, o.getPatch());
		if (result != 0) {
			return result;
		}
		return String.valueOf(label).compareTo(String.valueOf(o.getLabel()));
	}

	@Override
	public String toString() {
		return major + "." + minor + "." + patch + "_" + label;
	}
}
