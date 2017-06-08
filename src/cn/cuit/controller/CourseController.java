package cn.cuit.controller;

import cn.cuit.bo.impl.CourseService;
import cn.cuit.model.User;
import cn.cuit.util.AppUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Esong on 2017/6/8.
 */
@Controller
@RequestMapping(value = "/evaluate")
public class CourseController {
    @RequestMapping(value = "getCourse.do",method = RequestMethod.GET)
    public void getCourseInfo(HttpServletResponse response, HttpServletRequest request){
        JSONObject jsonData = new JSONObject();
        JSONObject user = (JSONObject)request.getSession().getAttribute("user");
        CourseService courseService = AppUtil.getCourseService();
        JSONObject jsonCourse = courseService.getCourseByUserNumber(user.getString("uNumber"));
        jsonData.put("user",user);
        jsonData.put("course",jsonCourse);
        System.out.println(jsonCourse);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonData.toString());
        } catch (IOException e) {
            System.out.println("写入数据流失败: "+e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
