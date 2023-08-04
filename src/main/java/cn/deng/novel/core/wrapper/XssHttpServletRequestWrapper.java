package cn.deng.novel.core.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Deng
 * @date 2023/8/4
 * @description
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Map<String, String> REPLACE_RULE = new HashMap<>();

    static {
        REPLACE_RULE.put("<", "&lt;");
        REPLACE_RULE.put(">", "&gt");
    }

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 重写获取参数值的方法
     * 返回请求参数的值
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values!=null){
            String[] escapeValues=new String[values.length];
            for (int i = 0; i < values.length; i++) {
                escapeValues[i] = values[i];
                int index = i;
                REPLACE_RULE.forEach(
                        (k, v) -> escapeValues[index] = escapeValues[index].replaceAll(k, v));
            }
            return escapeValues;
        }
        return new String[0];
    }
}
