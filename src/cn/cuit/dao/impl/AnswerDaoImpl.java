package cn.cuit.dao.impl;

import cn.cuit.dao.AnswerDao;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Esong on 2017/6/9.
 */
public class AnswerDaoImpl implements AnswerDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List getAnswer(String qtqId, String tsId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query hqlQuery = session.createQuery("from Answer a where a.qtq_Id=? and a.ts_Id=?");
        hqlQuery.setParameter(0,Integer.parseInt(qtqId)).setParameter(1,Integer.parseInt(tsId));
        List dataList = hqlQuery.getResultList();
        session.getTransaction().commit();
        return dataList;
    }

    @Override
    public boolean saveAnswer(String param) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlStr = "INSERT INTO answer_tab(ans_context,qtq_id,ts_id) VALUES"+param+" ON DUPLICATE KEY UPDATE ans_context=VALUES(ans_context)";
        Query sqlQuery = session.createNativeQuery(sqlStr);
        int count = sqlQuery.executeUpdate();
        session.getTransaction().commit();
        if (count == 0){
            return false;
        }else {
            return true;
        }
    }
}
