package cn.cuit.dao;

import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public interface TaskDao {
    List getTaskInfoByTTCId(String ttcId);
    String[] getTaskStateForUser(String userNumber, String tkId);
    List getQuestionListByQlId(String qlId);
    List getTaskInfoByTkId(String tkId);
    boolean updateTaskStateForUser(String tsId, int state);
}
