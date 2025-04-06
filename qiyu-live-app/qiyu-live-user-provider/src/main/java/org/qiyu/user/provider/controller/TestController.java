package org.qiyu.user.provider.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1")
public class TestController {

    @GetMapping("/dubbo")
    public String test(){
        System.out.println(1);
  return  "success";
    }
}
