package com.example.myspring.mvc;

import com.example.myspring.exception.requestException;
import com.example.myspring.mvc.annotations.RequestMapping;
import com.example.myspring.mvc.enums.RequestMethod;
import com.example.myspring.utils.PackageListUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class RequestScanner {
    public static void init() throws requestException {
        List<Class> list = PackageListUtils.getAllClass();
        for (Class c : list) {
            String classUrl = null;
            if (c.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
                classUrl = requestMapping.value();
            }
            Method[] methods = c.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                    String methodUrl = requestMapping.value();
                    RequestMethod requestMethod = requestMapping.method(); // 只看方法上的HTTP METHOD
                    MethodDetail detail=UrlMappingPool.getInstance().getMap(methodUrl,requestMethod);
                    if(detail!=null){
                        throw new requestException("多个方法请求路径和提交方法与"+c.getName()+":"+method.getName()+"重复");
                    }

                    methodUrl = classUrl == null ? methodUrl : classUrl + methodUrl;
                    log.info("Mapped URL [{} {}] onto handler of type [{}]", requestMethod.getValue(), methodUrl, method);
                    UrlMappingPool.getInstance().setMap(methodUrl, c, method, requestMethod);
                }
            }
        }

    }
}
