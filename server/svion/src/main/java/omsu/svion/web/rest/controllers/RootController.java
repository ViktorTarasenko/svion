package omsu.svion.web.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by victor on 15.03.14.
 */
@Controller
public class RootController {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void getStatusCode() {
    }
}
