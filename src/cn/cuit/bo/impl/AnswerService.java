package cn.cuit.bo.impl;

import cn.cuit.dao.AnswerDao;
import cn.cuit.model.Answer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Esong on 2017/6/9.
 */
public class AnswerService {
    private AnswerDao answerDao;

    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    public String getAnswer(String qtqId, String tsId) {
        List list = answerDao.getAnswer(qtqId, tsId);
        if (list.size() <= 0){
            return "unknown";
        }
        return ((Answer)list.get(0)).getAns_Context();
    }
    public boolean saveAnswer(JSONObject jsonObject) {
        StringBuffer strParam = new StringBuffer();
        String tsId = jsonObject.getString("tsId");
        JSONArray arrAnswers = jsonObject.getJSONArray("answer");
        for (int i = 0; i < arrAnswers.size(); i++){
            JSONObject answerObj = arrAnswers.getJSONObject(i);
            strParam.append("('");
            strParam.append(answerObj.getString("answer"));
            strParam.append("',");
            strParam.append(answerObj.getString("qtqId"));
            strParam.append(",");
            strParam.append(tsId);
            strParam.append(")");
            strParam.append(",");
        }
        strParam.delete(strParam.length()-1,strParam.length());
        System.out.println(strParam.toString());
        return answerDao.saveAnswer(strParam.toString());
    }
}
