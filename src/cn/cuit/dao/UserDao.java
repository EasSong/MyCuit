package cn.cuit.dao;

import cn.cuit.model.User;
import net.sf.json.JSONObject;

/**
 * Created by Esong on 2017/6/5.
 */
public interface UserDao {
    Object registerUser(String number, String password);
    JSONObject loginUser(User user);
    Object setUserInfo(Object object);
    boolean deleteUser(Object object);
}
