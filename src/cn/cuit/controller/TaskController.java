package cn.cuit.controller;

import cn.cuit.util.AppUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Esong on 2017/6/9.
 */
@Controller
@RequestMapping(value = "/evaluate")
public class TaskController {
    @RequestMapping(value = "getTaskInfo.do")
    public void getTaskInfo(HttpServletRequest request, HttpServletResponse response){
        String tsId = request.getParameter("tsId");
        String qlId = request.getParameter("qlId");
        String tkId = request.getParameter("tkId");
        JSONArray arrAnswerData = AppUtil.getTaskService().getQuestionListByQlId(qlId,tsId);
        JSONObject taskInfo = AppUtil.getTaskService().getTaskInfoByTkId(tkId);
        JSONObject jsonData = new JSONObject();
        jsonData.put("task",taskInfo);
        jsonData.put("question",arrAnswerData);
        response.setCharacterEncoding("UTF-8");
        System.out.println(jsonData);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonData.toString());
        } catch (IOException e) {
            System.out.println("读取问卷列表失败: "+e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
