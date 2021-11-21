package com.myspring;

import com.myspring.exception.creatException;
import com.myspring.exception.requestException;
import com.myspring.ioc.Context;
import com.myspring.mvc.RequestScanner;
import com.myspring.mvc.server.jetty.JettyServer;
import com.myspring.mvc.server.tomcat.MyTomcat;
import com.myspring.utils.ConfigUtil;
import org.apache.catalina.LifecycleException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Ms {
    public static void run() throws IllegalAccessException, InvocationTargetException, InstantiationException, creatException, requestException, LifecycleException, ClassNotFoundException {
        Context.init();
        RequestScanner.init();
        Map<String, Object> config = ConfigUtil.getConfig();
        String server = (String) config.get("server");
        int port = (Integer) config.get("port");
        // 启动HTTP服务器
        if ("jetty".equals(server))
            JettyServer.start(port);
        else if ("Tomcat".equals(server))
           MyTomcat.startServer();
        else throw new RuntimeException("Unknown server type [" + server + "]");

}

}
