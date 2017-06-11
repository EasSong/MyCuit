package cn.cuit.bo.impl;

import cn.cuit.dao.TaskDao;
import cn.cuit.util.AppUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Date;
import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public class TaskService {
    private TaskDao taskDao;

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    JSONArray getTaskInfoByTTCId(String ttcId, String userNumber){
        List dataList = taskDao.getTaskInfoByTTCId(ttcId);
        JSONArray dataArr = new JSONArray();
        for (Object obj:dataList) {
            Object[] row = (Object[]) obj;
            JSONObject rowData = new JSONObject();
            String beginDate = ((Date)row[2]).toString();
            String endDate = ((Date)row[3]).toString();
            rowData.put("tkId",row[0]);
            rowData.put("tkName",row[1]);
            String[] state = getTaskStateForUser(userNumber,String.valueOf(row[0]));
            rowData.put("state",state[0]);
            rowData.put("tsId",state[1]);
            rowData.put("begin", beginDate);
            rowData.put("end",endDate);
            rowData.put("qlId",row[4]);
            dataArr.add(rowData);
        }

        return dataArr;
    }
    public String[] getTaskStateForUser(String userNumber, String tkId){
        return taskDao.getTaskStateForUser(userNumber, tkId);
    }
    public JSONArray getQuestionListByQlId(String qlId,String tsId){
        JSONArray arrData = new JSONArray();
        List dataList = taskDao.getQuestionListByQlId(qlId);
        for (Object obj:dataList){
            JSONObject jsonObj = new JSONObject();
            Object[] row = (Object[])obj;
            jsonObj.put("qtqId",String.valueOf(row[0]));
            jsonObj.put("qName",String.valueOf(row[1]));
            jsonObj.put("qType",String.valueOf(row[2]));
            jsonObj.put("qOptions",String.valueOf(row[3]));
            jsonObj.put("answer", AppUtil.getAnswerService().getAnswer(String.valueOf(row[0]),tsId));
            arrData.add(jsonObj);
        }
        return arrData;
    }
    public JSONObject getTaskInfoByTkId(String tkId){
        JSONObject jsonTask = new JSONObject();
        List dataList = taskDao.getTaskInfoByTkId(tkId);
        if (dataList.size() <= 0){
            return null;
        }
        Object[] obj = (Object[])dataList.get(0);
        String beginDate = ((Date)obj[0]).toString();
        String endDate = ((Date)obj[1]).toString();
        String qlName = String.valueOf(obj[2]);
        jsonTask.put("time",beginDate+" жа "+endDate);
        jsonTask.put("qlName",qlName);
        return jsonTask;
    }
    public boolean updateTaskStateForUser(String tsId, int state){
        return taskDao.updateTaskStateForUser(tsId, state);
    }
}
