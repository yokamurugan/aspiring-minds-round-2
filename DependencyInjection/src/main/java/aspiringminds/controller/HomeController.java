package aspiringminds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aspiringminds.service.GreetingService;

@RestController
public class HomeController {

    @Autowired
    ApplicationContext appContext;

    @Autowired
    GreetingService greetingService;

    @RequestMapping("/")
    public String home() {
        return greetingService.greet();
    }

}
