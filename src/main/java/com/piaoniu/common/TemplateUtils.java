package com.piaoniu.common;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuran
 */
public class TemplateUtils {

    private static Pattern patternForVariable = Pattern.compile("[\\$#]\\{(\\w+)\\}", Pattern.CASE_INSENSITIVE);

    /**
     * 简单的表达式替换 <br>
     * e.g.
     * <pre>
     *     Map<String,Object> map = new HashMap<String, Object>();
     *     map.put("name","Tom");
     *     String result = TemplateUtils.format("My name is ${name}", map);
     *     assertThat(result).isEqualTo("My name is Tom");
     * </pre>
     *
     *
     * @param template ${key} as variable
     * @param context variable name and values
     * @return
     */
    public static String format(String template, Map<String, Object> context) {
        if (StringUtils.isBlank(template)) {
            return template;
        }
        StringBuffer accum = new StringBuffer();
        Matcher matcher = patternForVariable.matcher(template);
        while (matcher.find()) {
            String key = matcher.group(1);
            Preconditions.checkNotNull(context.get(key), "Key \"" + key + "\" must not be null");
            matcher.appendReplacement(accum, String.valueOf(context.get(key)));
        }
        matcher.appendTail(accum);
        return accum.toString();
    }
}
