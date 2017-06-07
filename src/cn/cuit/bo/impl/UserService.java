package cn.cuit.bo.impl;

import cn.cuit.dao.UserDao;
import cn.cuit.model.User;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Esong on 2017/6/6.
 */
public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public Object registerUser(String number, String password) {
        System.out.println("This system not accept register user!");
        return null;
    }

    public JSONObject loginUser(String name, String password) {
        User user = new User();
        user.setuNumber(name);
        user.setuPassword(password);

        return userDao.loginUser(user);
    }

    public Object setUserInfo(Object object) {
        return null;
    }

    public boolean deleteUser(Object object) {
        return false;
    }
}
