package com.example.myspring.mvc.server.jetty;


import com.example.myspring.ioc.BeansPool;
import com.example.myspring.mvc.MethodDetail;
import com.example.myspring.mvc.UrlMappingPool;
import com.example.myspring.mvc.annotations.Param;
import com.example.myspring.mvc.enums.RequestMethod;
import com.example.myspring.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class JettyServer {

    public static void start(int port) {
        Server server = new Server(port);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                response.setContentType("text/html; charset=utf-8");
                try {
                    doResponse(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 返回错误信息
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    e.printStackTrace(printWriter);
                    response.setContentType("text/plain; charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().print(stringWriter.toString());
                }
                baseRequest.setHandled(true);
            }
        });
        try {
            server.start();
            log.info("JettyServer is running on http://127.0.0.1:{}", port);
//            server.join();
        } catch (Exception e) {
            log.error("Starting Jetty error.");
            e.printStackTrace();
        }
    }

    /**
     * 解析请求并返回响应
     */
    private static void doResponse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.getEnum(request.getMethod());
        log.info("{} {}", requestMethod, url);
        //获取存有对应处理路径和请求方法的封装匹配规则对象
        MethodDetail methodDetail = UrlMappingPool.getInstance().getMap(url, requestMethod);
        // 如果找不到对应的匹配规则
        if (methodDetail == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(Constants.NOT_FOUND);
            throw new RuntimeException("can't find controller for " + url);
        }

        Class clazz = methodDetail.getClazz();
        Object object = BeansPool.getInstance().getObject(clazz);

        /**
         * 从容器中获取控制类的一个对象
         * 在Context中 扫描所有class文件并将所有有@Component注解的类（同时创建一个对象）注入BeanPool的map
         */
        if (object == null)
            throw new RuntimeException("未为"+ clazz+"注入依赖！");

        Map<String, String> requestParam = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> {
            requestParam.put(k, v[0]);
        });

        List<String> params = new ArrayList<>(); // 最终的方法参数
        Method method = methodDetail.getMethod();

        // 获取方法的所有的参数
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            String name = null;
            // 获取参数上所有的注解
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Param.class) {
                    Param param = (Param) annotation;
                    name = param.value();
                    break;
                }
            }
            // 如果请求参数中存在这个参数就把该值赋给方法参数，否则赋值null
            params.add(requestParam.getOrDefault(name, null));
        }

        Object result = method.invoke(object, params.toArray());

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(result);
    }

}
