package cn.cuit.controller;

import cn.cuit.util.AppUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Esong on 2017/6/9.
 */
@Controller
@RequestMapping(value = "/evaluate")
public class AnswerController {
    @RequestMapping(value = "submitAnswer.do",method = RequestMethod.POST)
    public void submitAnswers(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String answerStr = request.getParameter("answer");
        System.out.println(answerStr);
        JSONObject jsonObject = JSONObject.fromObject(answerStr);
        //System.out.println(jsonObject.get("tsId")+"+"+((jsonObject.getJSONArray("answer")).getJSONObject(4)).get("answer"));
        JSONObject jsonDate = new JSONObject();
        if(AppUtil.getAnswerService().saveAnswer(jsonObject)){
            if (AppUtil.getTaskService().updateTaskStateForUser(jsonObject.getString("tsId"),1)) {
                jsonDate.put("state", "success");
            }else {
                jsonDate.put("state","failed");
            }
        }else {
            jsonDate.put("state","failed");
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonDate.toString());
        } catch (IOException e) {
            System.out.println("×´Ì¬Ð´ÈëÁ÷Ê§°Ü£º"+e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
