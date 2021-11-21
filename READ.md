# 自实现一个简单版本的Spring
## 写在前面
[自我介绍](https://nbyxs.github.io/2021/09/16/hello-world/)

## 做这个项目的目的
单纯觉得是件很有意思的事情，凡事都带着目的性未免太没有意思了
一个新手的小玩具，大佬轻喷~~~
## 技术栈
java 1.8+
maven 3.8+

## 项目简单介绍
### 项目结构
```
com.myspring     
├── spring-aop             // aop实现，test含测试类
|
├── spring-common		  //公共集成 统一异常处理，utils等   
|
|
├── spring-ioc			 //ioc容器实现
|
├── spring-mvc			//内嵌Tomcat，netty等服务器，通过配置文件选择
|
├── spring-start	   //启动测试类
```
### aop实现
通过aopScanner类扫描标有注解@Aspect的类并扫描含有@Before和@After的方法

**注意：**
**1. Before和After内容为切入方法的完整名称，且同一个类中的Before和After方法只能切入同一个方法**

**2. Before和After方法需有static方法修饰，无需该类（Before和After所在类）实例化就反射执行方法**

**3. 或者在该类上注上@Componet注解实例化该类**

```java
@Aspect
public class Aspect2 {

    @Before("public abstract void com.myspring.dao.ISubject.execute()")
    public static void before() {
        System.out.println("[Aspect2] before advise");
    }
    
    @After("public abstract void com.myspring.dao.ISubject.execute()")
    public static void after() {
        System.out.println("[Aspect2] after advise");
    }
}
```

### ioc实现
目前已实现Bean，Component Configuration，Resource注解

注解使用方法与Spring注解一致。

1. 使用工具类PackageListUtils扫描所有class类，并将标有对应注解的类实例化并注入容器，并根据实体类.class获得
### 服务器嵌入
#### 以tomcat为例

1. 导入maven依赖：
```java
<dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-core</artifactId>
            <version>8.5.23</version>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-websocket</artifactId>
            <version>9.0.52</version>
</dependency>
<dependency>
            <groupId>org.apache.tomcat</groupId>
                 <artifactId>tomcat-juli</artifactId>
                <version>9.0.52</version>
</dependency>
```

2. 自定义tomcat
3. 继承HttpServlet并重写doGet和doPost方法

## 欢迎来我的博客逛逛~~
[博客地址](https://nbyxs.github.io/)