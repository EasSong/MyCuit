package cn.cuit.dao.impl;

import cn.cuit.dao.TaskDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public class TaskDaoImpl implements TaskDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List getTaskInfoByTTCId(String ttcId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlStr = "select * from task_tab where Ttc_id="+ttcId+";";
        Query sqlQuery = session.createNativeQuery(sqlStr);
        List listData = sqlQuery.getResultList();
        session.getTransaction().commit();
        return listData;
    }
}
