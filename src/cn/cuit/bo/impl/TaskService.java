package cn.cuit.bo.impl;

import cn.cuit.dao.TaskDao;
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
    JSONArray getTaskInfoByTTCId(String ttcId){
        List dataList = taskDao.getTaskInfoByTTCId(ttcId);
        JSONArray dataArr = new JSONArray();
        for (Object obj:dataList) {
            Object[] row = (Object[]) obj;
            JSONObject rowData = new JSONObject();
            String beginDate = ((Date)row[2]).toString();
            String endDate = ((Date)row[3]).toString();
            rowData.put("tkId",row[0]);
            rowData.put("tkName",row[1]);
            rowData.put("begin", beginDate);
            rowData.put("end",beginDate);
            rowData.put("qlId",row[4]);
            dataArr.add(rowData);
        }

        return dataArr;
    }
}
