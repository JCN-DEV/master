package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Authority;
import gov.step.app.domain.District;
import gov.step.app.domain.PersistentToken;
import gov.step.app.domain.User;
import gov.step.app.repository.AuthorityRepository;
import gov.step.app.repository.DistrictRepository;
import gov.step.app.repository.PersistentTokenRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.MailService;
import gov.step.app.service.UserService;
import gov.step.app.web.rest.dto.KeyAndPasswordDTO;
import gov.step.app.web.rest.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserService userService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private MailService mailService;

    @Inject
    private DistrictRepository districtRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * POST /register -> register the user.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        String userRole = userDTO.getAuthorities().toArray()[0].toString();

        return userRepository.findOneByLogin(userDTO.getLogin())
            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {

                    if(userDTO.getAuthorities().contains(AuthoritiesConstants.MPOCOMMITTEE)){
                        User user = userService.createCustomUserInformation(userDTO.getLogin(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                            userDTO.getLangKey(), AuthoritiesConstants.MPOCOMMITTEE,true);
                        String emailTemplate = "<html><body>" +
                            "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:100px;'><tbody>" +
                            "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                            "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate of Technical Education</td> </tr>" +

                            "<tr style='background-color:white; height:350px; vertical-align:top;'><td colspan='2'><p>Dear " + userDTO.getFirstName() + ",<br> DTE Added you to mpo committee. your username - " + user.getLogin() + " and password - " + userDTO.getPassword() + "'> . </p></td></tr>" +
                            "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +

                            "</table></center</body></html>";
                        mailService.sendEmail(user.getEmail(), "Mpo committee Login informaton", emailTemplate, false, true);
                        return new ResponseEntity<>("" + user.getId(), HttpStatus.CREATED);

                    }else {
                        User user = userService.createCustomUserInformation(userDTO.getLogin().toLowerCase(), userDTO.getPassword(),
                            userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
                            userDTO.getLangKey(), userRole);
                        String baseUrl = request.getScheme() + // "http"
                            "://" + // "://"
                            request.getServerName() + // "myhost"
                            ":" + // ":"
                            request.getServerPort() + // "80"
                            request.getContextPath(); // "/myContextPath"
                        // or "" if
                        // deployed in
                        // root context
                        Authority authority = authorityRepository.findOne(AuthoritiesConstants.EMPLOYER);
                        if(!user.getAuthorities().contains(authority)){
                            String emailTemplate = "<html><body>" +
                                "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:100px;'><tbody>" +
                                "<tr style='background-color:green;'><td style='text-align:right'></td>" +
                                "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate of Technical Education</td> </tr>" +

                                "<tr style='background-color:white; height:350px; vertical-align:top;'><td colspan='2'><p>Dear " + user.getLogin() + "<br>Click <a href='" + baseUrl + "/api/mail/active/" + user.getLogin() + "/" + user.getActivationKey() + "'> here</a> to active your account. </p></td></tr>" +
                                "<tr style='background-color:green;'><td colspan='2'' align='center'><p></p> </td></tr>" +

                                "</table></center</body></html>";
                            mailService.sendEmail(user.getEmail(), "Registration completed", emailTemplate, false, true);

                        }
                           return new ResponseEntity<>("" + user.getId(), HttpStatus.CREATED);
                    }

                   // mailService.sendActivationEmail(user, baseUrl);

                }));
    }


    /**
     * POST /register -> register the user.
     */
    @RequestMapping(value = "/registerDeo", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> registerDeoAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        String userRole = userDTO.getAuthorities().toArray()[0].toString();

        return userRepository.findOneByLogin(userDTO.getLogin())
            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService.createCustomUserInformation(userDTO.getLogin(), userDTO.getPassword(),
                        userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
                        userDTO.getLangKey(), userRole,true,userDTO.getDistrict());
                    String baseUrl = request.getScheme() + // "http"
                        "://" + // "://"
                        request.getServerName() + // "myhost"
                        ":" + // ":"
                        request.getServerPort() + // "80"
                        request.getContextPath(); // "/myContextPath"
                    // or "" if
                    // deployed in
                    // root context

                    mailService.sendActivationEmail(user, baseUrl);
                    return new ResponseEntity<>("" + user.getId(), HttpStatus.CREATED);
                }));
    }

    /**
     * POST /register -> register the Hrm user.
     */
    @RequestMapping(value = "/registerHrm", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> registerHrmAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        String userRole = userDTO.getAuthorities().toArray()[0].toString();
        log.debug("REST request to registerHrm");
        return userRepository.findOneByLogin(userDTO.getLogin())
            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService.createHrmCustomUserInformation(userDTO.getLogin(), userDTO.getPassword(),
                        userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
                        userDTO.getLangKey(), userRole);
                    String baseUrl = request.getScheme() + // "http"
                        "://" + // "://"
                        request.getServerName() + // "myhost"
                        ":" + // ":"
                        request.getServerPort() + // "80"
                        request.getContextPath(); // "/myContextPath"

                    //mailService.sendActivationEmail(user, baseUrl);
                    return new ResponseEntity<>("" + user.getId(), HttpStatus.CREATED);
                }));
    }

    /**
     * GET /activate -> activate the registered user.
     */
    @RequestMapping(value = "/mail/active/{login}/{activationKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void activateAccountFromEmail(@PathVariable String login, @PathVariable String activationKey, HttpServletRequest request,HttpServletResponse response) throws IOException {
       Optional<User> user =  userRepository.findOneByLogin(login);
        log.debug(" OPtional user to activate user :"+user);
        if(user != null && !user.get().getActivated() && user.get().getActivationKey().equals(activationKey)){
            userService.activateRegistration(activationKey);
            log.debug(">>>>>>>>>>>>>>>>>>>>>> :"+user.get().getLogin()+" has activated");
        }
        String baseUrl = request.getScheme() + // "http"
            "://" + // "://"
            request.getServerName() + // "myhost"
            ":" + // ":"
            request.getServerPort() + // "80"
            request.getContextPath(); // "/myContextPath"

        response.sendRedirect(baseUrl);
        //return baseUrl;
    }

    /**
     * GET /activate -> activate the registered user.
     */
    @RequestMapping(value = "/activate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return Optional.ofNullable(userService.activateRegistration(key))
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET /authenticate -> check if the user is authenticated, and return its
     * login.
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET /account -> get the current user.
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST /account -> update the current user information.
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
        return userRepository.findOneById(SecurityUtils.getCurrentUserId()).map(u -> {
            userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                userDTO.getLangKey());
            return new ResponseEntity<String>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST /change_password -> changes the current user's password
     */
    @RequestMapping(value = "/account/change_password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/account/sessions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        return userRepository.findOneById(SecurityUtils.getCurrentUserId())
            .map(user -> new ResponseEntity<>(persistentTokenRepository.findByUser(user), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/account/checkPassword/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> checkCurrentUserPass(@RequestParam String value) {
        String encryptedPassword = passwordEncoder.encode(value);
        Map map =new HashMap();

        map.put("value",value);
        if(passwordEncoder.matches(value,SecurityUtils.getCurrentUser().getPassword())){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
            //return new ResponseEntity<>("Correct password", HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
            //return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * GET /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/account/checkLogin/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
//    public ResponseEntity<Map> checkLogin( @RequestParam(name = "remark") String remark) {
    public ResponseEntity<Map> checkLogin( @RequestParam String value) {

        Optional<User> user = userRepository.findOneByLogin(value.toLowerCase());

        log.debug("user on check for----"+value);
        log.debug("user on check login----"+Optional.empty().equals(user));

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(user)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/account/checkEmail/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEmail(@RequestParam String value) {

        Optional<User> user = userRepository.findOneByEmail(value);
        log.debug("user on check email----"+Optional.empty().equals(user));

        Map map =new HashMap();

        map.put("value", value);

        if(Optional.empty().equals(user)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/account/checkDistrict/{value}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkDistrict(@PathVariable String value) {
       // log.debug("District check Value:"+value.toString());
        User user = districtRepository.findUserByDistrictId(Long.parseLong(value));

        Map map =new HashMap();

        map.put("value", value);

        if(user == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }


    /**
     * DELETE /account/sessions?series={series} -> invalidate an existing
     * session.
     * <p/>
     * - You can only delete your own sessions, not any other user's session -
     * If you delete one of your existing sessions, and that you are currently
     * logged in on that session, you will still be able to use that session,
     * until you quit your browser: it does not work in real time (there is no
     * API for that), it only removes the "remember me" cookie - This is also
     * true if you invalidate your current session: you will still be able to
     * use it until you close your browser or that the session times out. But
     * automatic login (the "remember me" cookie) will not work anymore. There
     * is an API to invalidate the current session, but there is no API to check
     * which session uses which cookie.
     */
    @RequestMapping(value = "/account/sessions/{series}", method = RequestMethod.DELETE)
    @Timed
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        userRepository.findOneById(SecurityUtils.getCurrentUserId()).ifPresent(u -> {
            persistentTokenRepository.findByUser(u).stream()
                .filter(persistentToken -> StringUtils.equals(persistentToken.getSeries(), decodedSeries)).findAny()
                .ifPresent(t -> persistentTokenRepository.delete(decodedSeries));
        });
    }

    @RequestMapping(value = "/account/reset_password/init", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
        return userService.requestPasswordReset(mail).map(user -> {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
            mailService.sendPasswordResetMail(user, baseUrl);
            return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
        }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/account/reset_password/finish", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) && password.length() >= UserDTO.PASSWORD_MIN_LENGTH
            && password.length() <= UserDTO.PASSWORD_MAX_LENGTH);
    }
}
