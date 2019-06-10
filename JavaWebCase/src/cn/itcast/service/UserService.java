package cn.itcast.service;

import cn.itcast.damain.Name;
import cn.itcast.damain.PageBean;
import cn.itcast.damain.Student;
import cn.itcast.damain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Student login(Student student);


    List<User> findAll();

    void delete(String[] ids);

    void add(User user);

    void update(User user);

    User findById(String id);

    PageBean<User> findByPage(String currentPage, String rows, Map<String, String[]> map);

    Name logName(Name name);
}
