package kr.co.duck.social;

import java.net.URI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.duck.beans.MemberBean;
import kr.co.duck.service.MemberService;

@Controller
@PropertySource("/WEB-INF/properties/application.properties")
public class ApiController {

    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.login.url}")
    private String googleLoginUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.redirect.url}")
    private String googleRedirectUrl;

    @Value("${google.secret}")
    private String googleClientSecret;
    
    @Autowired
    private MemberService memberService;
    
    @Resource(name = "loginMemberBean")
	private MemberBean loginMemberBean;

    // 구글 로그인창 호출
    @GetMapping("/member/getGoogleAuthUrl")
    public ResponseEntity<?> getGoogleAuthUrl(HttpServletRequest request) throws Exception {
        String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(reqUrl));
        //1.reqUrl 구글로그인 창을 띄우고, 로그인 후 /user/socialLogin 으로 리다이렉션하게 한다.
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
    
    // 구글에서 리다이렉션
    @GetMapping("/user/socialLogin")
    public String oauth_google_check(HttpServletRequest request,
                                     @RequestParam(value = "code") String authCode,
                                     HttpServletResponse response) throws Exception{
        //2.약속된 토큰을 받기위한 객체 생성
        GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .code(authCode)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();
        RestTemplate restTemplate = new RestTemplate();
        //3.토큰요청을 한다.
        ResponseEntity<GoogleLoginResponse> apiResponse = restTemplate.postForEntity(googleAuthUrl + "/token", googleOAuthRequest, GoogleLoginResponse.class);
        //4.받은 토큰을 토큰객체에 저장
        GoogleLoginResponse googleLoginResponse = apiResponse.getBody();

        String googleToken = googleLoginResponse.getId_token();
        //5.받은 토큰을 구글에 보내 유저정보를 얻는다.
        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token",googleToken).toUriString();
        //6.허가된 토큰의 유저정보를 결과로 받는다.
        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(resultJson);
        
        String email = jsonNode.get("email").asText();
        String name = jsonNode.get("name").asText();
        String sub = jsonNode.get("sub").asText();
        
        String userEmail = email;
        String userName = name;
        String userPw = sub;
		/* memberService.addGoogleUserInfo(userEmail,userPw,userEmail,userName); */
        
        return "redirect:/main";
    }
}