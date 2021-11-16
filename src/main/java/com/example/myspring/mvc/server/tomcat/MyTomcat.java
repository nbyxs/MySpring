package com.example.myspring.mvc.server.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class MyTomcat {
    private static Tomcat tomcat;
    public  static void startServer() throws LifecycleException {
        //实例化tomcat
        tomcat = new Tomcat();
        tomcat.setPort(8899);
        tomcat.start();
        //实例化context容器
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        TomcatServer testServlet = new TomcatServer();
        Tomcat.addServlet(context,"testServlet",testServlet).setAsyncSupported(true);

        //添加URL映射
        context.addServletMappingDecoded("/","testServlet");
        tomcat.getHost().addChild(context);

        //设置守护线程防止tomcat中途退出
        Thread awaitThread = new Thread("tomcat_await_thread."){
            @Override
            public void run() {
                tomcat.getServer().await();
            }
        };
        //设置为非守护线程
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

}
