package cn.cuit.bo.impl;

import cn.cuit.dao.CourseDao;
import cn.cuit.model.User;
import cn.cuit.util.AppUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public class CourseService {
    private CourseDao courseDao;

    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public JSONObject getCourseByUserNumber(String uNumber){
        User user = new User();
        user.setuNumber(uNumber);
        List dataList =  courseDao.getCourseByUserNumber(user);
        JSONObject jsonData = new JSONObject();
        JSONArray jsonXX = new JSONArray();
        JSONArray jsonBX = new JSONArray();
        JSONArray jsonHX = new JSONArray();
        for (Object obj:dataList){
            Object[] row = (Object[])obj;
            String couType = String.valueOf(row[2]);
            JSONObject jsonCou = new JSONObject();
            JSONArray taskJsonArr = AppUtil.getTaskService().getTaskInfoByTTCId(String.valueOf(row[6]));
            jsonCou.put("teacherName",row[1]);
            jsonCou.put("couTime",row[3]);
            jsonCou.put("couCredit",row[4]);
            jsonCou.put("couName",row[5]);
            jsonCou.put("ttcId",row[6]);
            if (taskJsonArr.size()<=0){
                jsonCou.put("task","no task");
            }else{
                jsonCou.put("task",taskJsonArr);
            }
            switch (couType){
                case "Ñ¡ÐÞ":
                    jsonXX.add(jsonCou);
                    break;
                case "±ØÐÞ":
                    jsonBX.add(jsonCou);
                    break;
                case "ºËÐÄ":
                    jsonHX.add(jsonCou);
                    break;
                default:
                    break;
            }
        }
        jsonData.put("XX",jsonXX);
        jsonData.put("BX",jsonBX);
        jsonData.put("HX",jsonHX);

        return jsonData;
    }
}
