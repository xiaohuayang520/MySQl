package cn.itcast.service.impl;

import cn.itcast.damain.Name;
import cn.itcast.damain.PageBean;
import cn.itcast.damain.Student;
import cn.itcast.damain.User;
import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static UserDao dao = new UserDaoImpl();
    @Override
    public Student login(Student student) {

        return dao.login(student.getUsername(),student.getPassword());
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public void delete(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                dao.delete(Integer.parseInt(id));
            }
        }

    }

    @Override
    public void add(User user) {
        dao.add(user);
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public User findById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public PageBean<User> findByPage(String currentPage, String rows, Map<String, String[]> map) {
        PageBean<User> bean = new PageBean<>();

        int totalCount = dao.findTotalCount(map);//select count(*) from user,总数据条数
        int page = Integer.parseInt(currentPage);//当前的页码
        int rowsCount = Integer.parseInt(rows);//每页显示的条数
        int start = (page-1)*rowsCount;//开始的查询的索引

        List<User> list = dao.findCurrentPageUser(start,rowsCount,map);//select * from user limit 开始,5
        bean.setCurrentPage(page);
        bean.setList(list);
        bean.setRows(rowsCount);
        bean.setTotalCount(totalCount);
        int totalPage = totalCount%rowsCount==0?totalCount/rowsCount:totalCount/rowsCount+1;//总页码
        bean.setTotalPage(totalPage);

        if (page >= totalPage) {
            page = totalPage;
        }


        return bean;
    }

    @Override
    public Name logName(Name name) {

        return dao.logName(name.getUsername());
    }
}
