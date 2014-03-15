package omsu.svion.web.rest.controllers;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import omsu.svion.dao.AccountService;
import omsu.svion.entities.Account;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by victor on 15.03.14.
 */
@Controller
public class GoogleAuthenticationController {
    private static  final Logger logger = Logger.getLogger(GoogleAuthenticationController.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccountService accountService;
    @RequestMapping(value = "/authorize/google",method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @Transactional
    public void authenticate(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        UserDetails userDetails = null;
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(),null).build();
        Userinfo info = oauth2.userinfo().get().setOauthToken(token).execute();
        logger.debug("email is "+info.getEmail());
        logger.debug("token is "+token);
        try {
            userDetails = userDetailsService.loadUserByUsername(info.getEmail());
        }
        catch (UsernameNotFoundException e) {
            Account account = new Account();
            account.setEmail(info.getEmail());
            accountService.createAccount(account);
            userDetails = userDetailsService.loadUserByUsername(info.getEmail());
        }
        finally {
            Authentication authentication =  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
