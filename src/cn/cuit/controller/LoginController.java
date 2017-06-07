package cn.cuit.controller;

/**
 * Created by Esong on 2017/5/31.
 */

import cn.cuit.bo.impl.CourseService;
import cn.cuit.dao.impl.UserDaoImpl;
import cn.cuit.model.User;
import cn.cuit.util.AppUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/evaluate")
public class LoginController {
    private static final String OAUTHCALLBACK = "https://app.cuit.edu.cn/v1/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=cuitevaluate&redirect_uri=http://192.168.1.201:8080/Login/oauthCallback";

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    public String Login(HttpSession session, ModelMap modelMap) {
        if (session.getAttributeNames().hasMoreElements()) {
            Object user = session.getAttribute("user");
            if (user != null) {
                System.out.println("user is login!");
                return "redirect:/mycuit/evaluate/starter.html";
            } else {
                System.err.println("user is logout!");
                return "redirect:/mycuit/evaluate/login.html";
            }
        } else {
            System.err.println("user not login!");
            //return "redirect:" + OAUTHCALLBACK;
            return "redirect:/mycuit/evaluate/login.html";
            //return "login";
        }
    }

    @RequestMapping(value = "checkLogin.do",method = RequestMethod.GET)
    public void checkLogin(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        JSONObject jsonData = new JSONObject();

        if (session.getAttributeNames().hasMoreElements()) {
            JSONObject user = (JSONObject) session.getAttribute("user");
            if (user != null) {
                System.out.println("user is login!");
                jsonData.put("state","login");
                jsonData.put("user",user);
                CourseService courseService = AppUtil.getCourseService();
                JSONObject jsonCourse = courseService.getCourseByUserNumber(user.getString("uNumber"));
                jsonData.put("course",jsonCourse);
                System.out.println(jsonCourse);
            } else {
                System.err.println("user is logout!");
                jsonData.put("state","logout");
            }
        } else {
            System.err.println("user not login!");
            jsonData.put("state","logout");
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @RequestMapping(value = "oauthCallback.do", method = RequestMethod.GET)
    public String oauthCallback(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        String codeStr = request.getParameter("code");
        System.out.println("code:" + codeStr);
        session.setAttribute("user", "test" + codeStr);
        modelMap.addAttribute("message", codeStr);

        String payUrl = "http://app.cuit.edu.cn/v1/oauth/token";// POST提交地址
        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化post方法
        HttpPost httpPost = new HttpPost(payUrl);
        //封装参数
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nvps.add(new BasicNameValuePair("client_id", "cuitevaluate"));
        nvps.add(new BasicNameValuePair("client_secret", "evaluate2017"));
        nvps.add(new BasicNameValuePair("code", codeStr));
        //结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //提交的参数
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            //将参数给post方法
            httpPost.setEntity(uefEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                JSONObject jsonObject = JSONObject.fromObject(content);
                JSONObject result = jsonObject.getJSONObject("result");
                System.out.println(content);
                return "redirect:/Login/getUserInfo.do?tokenType="+result.get("tokenType")+"&accessToken="+result.get("accessToken");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "getUserInfo.do", method = RequestMethod.GET)
    public String postCallback(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        String tokenType = request.getParameter("tokenType");
        String accessToken = request.getParameter("accessToken");

        String urlNameString = "http://app.cuit.edu.cn/v1/user";
        String result="";
        try {
            // 根据地址获取请求
            HttpGet requestGet = new HttpGet(urlNameString);//这里发送get请求
            requestGet.setHeader("Authorization","Bearer "+accessToken);
            // 获取当前客户端对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(requestGet);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println(result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return "HelloTest";
    }
    @RequestMapping(value = "userLogin.do",method = RequestMethod.POST)
    public void userLogin(HttpServletRequest request, HttpServletResponse response){
        String userNumber = request.getParameter("number");
        String userPassword = request.getParameter("password");

        JSONObject userInfo = AppUtil.getUserService().loginUser(userNumber,userPassword);

        System.out.println(userInfo);

        if (userInfo.getInt("stateCode") == UserDaoImpl.LOGIN_SUCCESS){
            HttpSession session = request.getSession();
            session.setAttribute("user",userInfo.get("userInfo"));
        }

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(userInfo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out!=null){
                out.close();
            }
        }
    }
}
