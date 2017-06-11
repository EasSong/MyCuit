package cn.cuit.dao;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Esong on 2017/6/9.
 */
public interface AnswerDao {
    List getAnswer(String qtqId, String tsId);
    boolean saveAnswer(String param);
}
