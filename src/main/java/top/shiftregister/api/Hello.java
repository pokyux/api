package top.shiftregister.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello! This API server is powered by Spring!";
    }
}
