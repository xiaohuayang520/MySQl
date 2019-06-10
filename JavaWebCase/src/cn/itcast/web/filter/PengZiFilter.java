package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

//@WebFilter("/*")
public class PengZiFilter implements Filter {
    private ArrayList<String> list = new ArrayList<>();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletRequest proxyInstance = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getParameter")) {
                    String value = (String) method.invoke(req, args);

                    if (value!=null) {
                        for (String s : list) {
                            if (value.contains(s)) {
                                value = value.replaceAll(s, "***");
                            }
                        }
                    }
                    return value;
                }


                return method.invoke(req, args);
            }
        });

        //放行
        chain.doFilter(proxyInstance, resp);
    }

    public void init(FilterConfig config) throws ServletException {

        /*try {
            //获取文件的真实路径
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            //用高效缓冲流读取文件
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            String len;
            while ((len = br.readLine()) != null) {
                //把读取的内容存放到集合
                list.add(len);
            }
            br.close();
        }catch (IOException e) {
            e.printStackTrace();
        }*/

        list.add("坏蛋");
        list.add("笨蛋");

    }

}
