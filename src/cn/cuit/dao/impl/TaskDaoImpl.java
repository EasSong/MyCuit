package cn.cuit.dao.impl;

import cn.cuit.dao.TaskDao;
import cn.cuit.model.TaskState;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.Serializable;
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

    @Override
    public String[] getTaskStateForUser(String userNumber, String tkId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlStr = "from TaskState ts where ts.tkId=? and ts.uNumber=?";
        Query sqlQuery = session.createQuery(sqlStr);
        sqlQuery.setParameter(0,Integer.parseInt(tkId)).setParameter(1,userNumber);
        List listData = sqlQuery.list();
        String[] result = new String[2];
        TaskState taskState;
        if (listData.size() <= 0){
            result[0] = "0";
            taskState = new TaskState(Integer.parseInt(tkId),userNumber,0);
            session.save(taskState);
            result[0] = String.valueOf(taskState.getTsState());
            result[1] = String.valueOf(taskState.getId());
            System.out.print(taskState.getId());
        }else{
            taskState = (TaskState)listData.get(0);
            result[0] = String.valueOf(taskState.getTsState());
            result[1] = String.valueOf(taskState.getId());
        }
        session.getTransaction().commit();
        return result;
    }

    @Override
    public List getQuestionListByQlId(String qlId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlStr = "select qtq.qtq_id,q.q_name,q.q_type,q.q_options from qtltoqt_tab qtq,question_tab q where qtq.q_id = q.q_id and qtq.ql_id="+qlId+" order by q.q_type";
        Query sqlQuery = session.createNativeQuery(sqlStr);
        List questionList = sqlQuery.getResultList();
        session.getTransaction().commit();
        return questionList;
    }

    @Override
    public List getTaskInfoByTkId(String tkId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlStr = "select tk.tk_begin,tk.tk_end,ql.ql_name from task_tab tk,questionslist_tab ql where tk_id ="+tkId+" and tk.ql_id=ql.ql_id";
        Query sqlQuery = session.createNativeQuery(sqlStr);
        List dataList = sqlQuery.getResultList();
        session.getTransaction().commit();
        return dataList;
    }

    @Override
    public boolean updateTaskStateForUser(String tsId, int state) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query hqlQuery = session.createQuery("update TaskState ts set ts.tsState=? where ts.id=?");
        hqlQuery.setParameter(0,state).setParameter(1,Integer.parseInt(tsId));
        int count = hqlQuery.executeUpdate();
        session.getTransaction().commit();
        if (count <= 0){
            return false;
        }else{
            return true;
        }
    }

}
