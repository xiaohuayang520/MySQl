package cn.itcast.dao.impl;

import cn.itcast.damain.Name;
import cn.itcast.damain.Student;
import cn.itcast.damain.User;
import cn.itcast.dao.UserDao;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 登录查询
     * @param username
     * @param password
     * @return
     */
    @Override
    public Student login(String username,String password) {
        try {
            String sql = "select * from student where username = ? and password = ?";
            Student student = template.queryForObject(sql, new BeanPropertyRowMapper<Student>(Student.class), username, password);
            return student;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql,id);
    }

    @Override
    public void add(User user) {
        String sql = "insert into user value (null,?,?,?,?,?,?)";
        template.update(sql, user.getName(), user.getSex(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void update(User user) {
        String sql = "update user set name = ?,sex = ?,age =?,address=?,qq=?,email=? where id = ?";
        template.update(sql,user.getName(), user.getSex(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(),user.getId());
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return user;
    }

    @Override
    public int findTotalCount(Map<String, String[]> map) {
        String sql = "select count(*) from user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }
            String value = map.get(key)[0];
            //判断value是否有值
            if (value != null && !value.equals("")) {
                sb.append(" and " + key + " like  ? ");
                params.add("%" +value+ "%");
            }
        }

        System.out.println(sb.toString());
        System.out.println(params);
        return template.queryForObject(sb.toString(),int.class,params.toArray());
    }

    @Override
    public List<User> findCurrentPageUser(int start, int rowsCount, Map<String, String[]> map) {
        String sql = "select * from user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }
            String value = map.get(key)[0];
            //判断value是否有值
            if (value != null && !value.equals("")) {
                sb.append(" and " + key + " like  ? ");
                params.add("%" +value+ "%");
            }
        }
        sb.append(" limit ?,?");
        params.add(start);
        params.add(rowsCount);
        //sql = sb.toString();
        List<User> list = template.query(sb.toString(), new BeanPropertyRowMapper<>(User.class),params.toArray());
        return list;
    }

    @Override
    public Name logName(String name) {
        try {
            String sql = "select * from name where username = ?";
            Name logname = template.queryForObject(sql, new BeanPropertyRowMapper<Name>(Name.class), name);
            return logname;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }
}
