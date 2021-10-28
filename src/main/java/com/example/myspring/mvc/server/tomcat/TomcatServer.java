package com.example.myspring.mvc.server.tomcat;




import com.example.myspring.exception.requestException;
import com.example.myspring.ioc.BeansPool;
import com.example.myspring.mvc.MethodDetail;
import com.example.myspring.mvc.UrlMappingPool;
import com.example.myspring.mvc.annotations.Param;
import com.example.myspring.mvc.enums.RequestMethod;
import com.example.myspring.utils.Constants;

import lombok.extern.slf4j.Slf4j;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TomcatServer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       service(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      doGet(req,resp);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestMethod method= RequestMethod.getEnum(request.getMethod());
        if(method==null){
            log.info(request.getMethod()+"请求方法未知！");
        }
        String url=request.getRequestURI();
        log.info("{} {}", method, url);
        MethodDetail methodDetail = UrlMappingPool.getInstance().getMap(url, method);
        // 如果找不到对应的匹配规则
        if (methodDetail == null) {
            response.setStatus(javax.servlet.http.HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(Constants.NOT_FOUND);

            throw new requestException(url+"未匹配到对应处理方法！");

        }

        Class c=methodDetail.getClazz();
        Object object = BeansPool.getInstance().getObject(c);
        if(object==null){
            throw new requestException("未为"+c+"创建依赖对象！");

        }
        Map<String, String> requestParam = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> {
            requestParam.put(k, v[0]);
        });
        List<String> params = new ArrayList<>(); // 最终的方法参数
        Method method_controller = methodDetail.getMethod();
        Parameter[] parameters=method_controller.getParameters();
        for(Parameter parameter:parameters){
            String name=null;
            Annotation[] annotations=parameter.getAnnotations();
            for(Annotation annotation:annotations){
                if(annotation.annotationType().equals(Param.class)){
                    Param param = (Param) annotation;
                    name = param.value();
                    break;
                }
            }
            params.add(requestParam.getOrDefault(name,null));

        }
        try {
            Object result=method_controller.invoke(object,params.toArray());
            response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
            response.getWriter().print(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }


}
