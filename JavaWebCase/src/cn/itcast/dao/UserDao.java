package cn.itcast.dao;

import cn.itcast.damain.Name;
import cn.itcast.damain.Student;
import cn.itcast.damain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public Student login(String username,String password);

    List<User> findAll();

    void delete(int i);

    void add(User user);

    void update(User user);

    User findById(int i);

    int findTotalCount(Map<String, String[]> map);

    List<User> findCurrentPageUser(int start, int rowsCount, Map<String, String[]> map);

    Name logName(String name);
}
