package bruno.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("gnight")
    public String goodNight() {
        return "Inside the method \"Good Night\"";
    }
}
