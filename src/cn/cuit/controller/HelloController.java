package cn.cuit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping(value = "print",method = RequestMethod.GET)
    public String printHello(ModelMap modelMap){
        modelMap.addAttribute("message","Hello Spring MVC Framework!");
        return "HelloTest";
    }
}
