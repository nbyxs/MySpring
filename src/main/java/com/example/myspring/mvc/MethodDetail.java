package com.example.myspring.mvc;


import com.example.myspring.mvc.enums.RequestMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 与某个HTTP的URL映射的Java方法的详细信息
 *
 * @author hourui 2017/10/27 20:25
 */
@Getter
@Setter
@Builder
public class MethodDetail {

    private String url;
    private RequestMethod requestMethod;
    private Class clazz;
    private Method method;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodDetail that = (MethodDetail) o;
        return url.equals(that.url) &&
                requestMethod == that.requestMethod &&
                clazz.equals(that.clazz) &&
                method.equals(that.method);
    }
    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod, clazz, method);
    }
}
