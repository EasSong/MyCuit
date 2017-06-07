package cn.cuit.dao.impl;

import cn.cuit.dao.UserDao;
import cn.cuit.model.User;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Esong on 2017/6/6.
 */
public class UserDaoImpl implements UserDao {
    /*
    Login state code
     */
    public final static int LOGIN_SUCCESS = 1;
    public final static int LOGIN_DATABASE_ERROR = 2;
    public final static int LOGIN_USER_EXITS = 3;
    public final static int LOGIN_PASSWORD_ERROR = 4;
    /*
    Login state information
     */
    private final static String[] LOGIN_STATE_INFO = {"Login Success!",
                                                      "Database happened a error!",
                                                      "Don`t have this user!",
                                                      "The password is wrong!"};

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Object registerUser(String number, String password) {
        System.out.println("This system not accept register user!");
        return null;
    }

    @Override
    public JSONObject loginUser(User user) {
        JSONObject userJson = new JSONObject();
        int stateCode = LOGIN_USER_EXITS;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query hqlQuery = session.createQuery("from User u where u.uNumber=?");
        hqlQuery.setParameter(0,user.getuNumber());
        List listUser = hqlQuery.getResultList();
        if (listUser.size()<=0){
            userJson.put("userInfo",null);
            stateCode = LOGIN_USER_EXITS;
        }
        else if (!((User)listUser.get(0)).getuPassword().equals(user.getuPassword())){
            userJson.put("userInfo","Unknown");
            stateCode = LOGIN_PASSWORD_ERROR;
        }else{
            userJson.put("userInfo",listUser.get(0));
            stateCode = LOGIN_SUCCESS;
        }
        userJson.put("stateCode",stateCode);
        userJson.put("stateInfo",LOGIN_STATE_INFO[stateCode-1]);
        session.getTransaction().commit();

        return userJson;
    }

    @Override
    public Object setUserInfo(Object object) {
        return null;
    }

    @Override
    public boolean deleteUser(Object object) {
        return false;
    }
}
