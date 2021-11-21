package com.myspring;

import com.myspring.exception.requestException;
import com.myspring.annotations.RequestMapping;
import com.myspring.enums.RequestMethod;
import com.myspring.utils.PackageListUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
/**
 *
 *
 * @author nbyxs
 * @date 2021/11/17 11:46
 * @param
 * @return
 * 请求映射处理
 */

@Slf4j
public class RequestScanner {
    public static void init() throws requestException {
        List<Class> list = PackageListUtils.getAllClass();
        UrlMappingPool urlMappingPool=UrlMappingPool.getInstance();
        for (Class c : list) {
            String classUrl = null;
            if (c.isAnnotationPresent(RequestMapping.class)) {
                //获取 RequestMapper注解 控制类和对应公共前缀
                RequestMapping requestMapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
                classUrl = requestMapping.value();
            }
            Method[] methods = c.getMethods();
            for (Method method : methods) {
                //再查询控制类对应有RequestMapper注解的方法
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                    String methodUrl = requestMapping.value();
                    RequestMethod requestMethod = requestMapping.method();
                    // 只看方法上的HTTP METHOD

                    //查询请求路径和请求方法 对应的方法是否已有处理方法
                    MethodDetail detail_exist=urlMappingPool.getMap(methodUrl,requestMethod);


                    if(detail_exist!=null){
                        throw new requestException("多个方法请求路径和提交方法与"+c.getName()+":"+method.getName()+"重复");
                    }

                    methodUrl = classUrl == null ? methodUrl : classUrl + methodUrl;
                    log.info("Mapped URL [{} {}] onto handler of type [{}]", requestMethod.getValue(), methodUrl, method);
                   //将 方法处理路径 控制类 控制类特定方法 提交方法 封装进map
                    urlMappingPool.setMap(methodUrl, c, method, requestMethod);
                }
            }
        }

    }
}
