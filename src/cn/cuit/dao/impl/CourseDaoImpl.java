package cn.cuit.dao.impl;


import cn.cuit.dao.CourseDao;
import cn.cuit.model.User;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public class CourseDaoImpl implements CourseDao{
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List getCourseByUserNumber(User user) {
        String sqlStr = "select stt.u_number as stuNumber,ttc.u_number as teachNumber, ttc_type as couType, ttc_time as couTime,ttc_credit as couCredit,cou.cou_name as couName,ttc.ttc_id as ttcId from stutottc_tab stt,teachertocourse_tab ttc,course_tab cou where stt.u_number='"+user.getuNumber()+"' and stt.ttc_id=ttc.ttc_id and ttc.cou_id=cou.cou_id order by ttc.ttc_type;";
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query sqlQuery = session.createNativeQuery(sqlStr);
        List couInfoList = sqlQuery.getResultList();
        session.getTransaction().commit();

        return couInfoList;
    }
}
