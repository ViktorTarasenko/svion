package omsu.svion.web.rest.controllers;

import omsu.svion.dao.QuestionService;
import omsu.svion.messages.QuestionMessage;
import omsu.svion.model.json.SimpleResponse;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.util.LinkedList;

/**
 * Created by victor on 12.03.14.
 */
@Controller
public class HelloWorldController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET, produces="application/json")
    public @ResponseBody SimpleResponse getHello() {
        return new SimpleResponse("test ");
    }

}
