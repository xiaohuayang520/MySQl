package cn.itcast.web.servlet;

import cn.itcast.damain.Name;
import cn.itcast.damain.Student;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/FindServlet")
public class FindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*//为避免乱码，设置编码格式
        response.setContentType("application/json;charset=utf-8");
        //获取从客服端传来的数据
        String username = request.getParameter("username");
        Map<String, Object> map = new HashMap<String,Object>();
        //判断是否存在
        if ("jok".equals(username)) {
            map.put("userExsit", true);
            map.put("msg", "此用户名太受欢迎,请更换一个");
        } else {
            map.put("userExsit", false);
            map.put("msg", "用户名可用");
        }
        //把map集合从Java对象转换JSON
        ObjectMapper mapper = new ObjectMapper();
        //并传递给客服端
        mapper.writeValue(response.getWriter(),map);*/
        response.setContentType("application/json;charset=utf-8");
        //String username = request.getParameter("username");

        Map<String, String[]> m = request.getParameterMap();
        Name name = new Name();
        try {
            BeanUtils.populate(name,m);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String,Object>();


        UserService service = new UserServiceImpl();
        Name logName = service.logName(name);

        if (logName!=null) {
            map.put("userExsit", true);
            map.put("msg", "此用户名太受欢迎,请更换一个");
        } else {
            map.put("userExsit", false);
            map.put("msg", "用户名可用");
        }
        //把map集合从Java对象转换JSON
        ObjectMapper mapper = new ObjectMapper();
        //并传递给客服端
        mapper.writeValue(response.getWriter(),map);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
