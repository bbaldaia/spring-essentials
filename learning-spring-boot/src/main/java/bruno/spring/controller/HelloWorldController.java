package bruno.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("hello")
    public String helloWorld() {
        return "Inside the method \"Hello World\"";
    }

    @GetMapping("bye")
    public String goodbyeWorld() {
        return "Inside the method \"Goodbye World\"";
    }
}
