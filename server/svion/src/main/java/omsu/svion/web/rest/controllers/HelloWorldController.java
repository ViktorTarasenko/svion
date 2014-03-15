package omsu.svion.web.rest.controllers;

import omsu.svion.model.json.SimpleResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by victor on 12.03.14.
 */
@Controller
public class HelloWorldController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET, produces="application/json")
    public @ResponseBody SimpleResponse getHello() {
        return new SimpleResponse("привет, х*сосы!");
    }
}
