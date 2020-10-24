package io.swagger.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to swagger api documentation
 */
@Controller
@Log4j2
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
        log.info("Redirected >> swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
