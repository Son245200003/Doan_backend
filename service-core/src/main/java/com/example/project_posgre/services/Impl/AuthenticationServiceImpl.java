package com.example.project_posgre.services.Impl;

import com.example.project_posgre.component.JwtTokenUtils;
import com.example.project_posgre.dtos.requests.LoginRequest;
import com.example.project_posgre.dtos.requests.ResetPasswordDTO;
import com.example.project_posgre.exception.AccountAlreadyExist;
import com.example.project_posgre.exception.InvalidParamException;
import com.example.project_posgre.exception.NotFoundException;
import com.example.project_posgre.models.RedisToken;
import com.example.project_posgre.models.Role;
import com.example.project_posgre.models.Token;
import com.example.project_posgre.models.User;
import com.example.project_posgre.dtos.reponses.LoginResponse;
import com.example.project_posgre.repository.RoleRepository;
import com.example.project_posgre.repository.UserRepository;
import com.example.project_posgre.services.AuthenticationService;
import com.example.project_posgre.services.RedisTokenService;
import com.example.project_posgre.services.TokenService;
import com.example.project_posgre.utils.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RedisTokenService redisTokenService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final EmailService emailService;
    @Value("${jwt.expiration}")
    private int expirationAccessToken;

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user=userRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("Sai tk hoac mk"));
//        Role role=roleRepository.findById(loginRequest.getRole())
//                .orElseThrow(() -> new NotFoundException("Ko tim thay role"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new InvalidParamException("Sai mật khẩu or tai khoan");
        }


        if(!user.isActive()){
            throw new AccountAlreadyExist("Tai khoan cua ban dang bi khoa");
        }
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                loginRequest.getPhoneNumber(),
                loginRequest.getPassword(),
                user.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);

        String accessToken= jwtTokenUtils.generateToken(user);
        String refreshToken= jwtTokenUtils.generateRefreshToken(user);

//        tokenService.save(Token.builder()
//                        .phonenumber(user.getPhoneNumber())
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .expiration_date(LocalDateTime.now().plusSeconds(expirationAccessToken))
//                        .refreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken))
//                .build());
        String id=redisTokenService.save(RedisToken.builder()
                        .id(user.getPhoneNumber())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .expirationDate(LocalDateTime.now().plusSeconds(expirationAccessToken))
                        .resetTokenExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken))
                .build());
        log.info("-------------------login=--------------"+id);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .role(user.getRoleId().getName())
                .build();
    }

    @Override
    public LoginResponse refresh(HttpServletRequest request) {
        // Lấy refresh token từ header "x-token"
        String refreshToken = request.getHeader("x-token");

        // Kiểm tra nếu refreshToken là null hoặc rỗng
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new InvalidParamException("Refresh token is null or empty.");
        }
        // Trích xuất số điện thoại từ refresh token
        final String phoneNumber = jwtTokenUtils.extractPhonenumber(refreshToken, TokenType.REFRESH_TOKEN);

        // Tìm người dùng theo số điện thoại
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found with phone number: " + phoneNumber));

        // Kiểm tra xem refresh token có hợp lệ không
        if (!jwtTokenUtils.validateToken(refreshToken, user,TokenType.REFRESH_TOKEN)) {
            throw new InvalidParamException("Refresh token is expired or invalid.");
        }

        // Tạo mới access token
        String accessToken = jwtTokenUtils.generateToken(user);

        redisTokenService.save(RedisToken.builder()
                .id(user.getPhoneNumber())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expirationDate(LocalDateTime.now().plusSeconds(expirationAccessToken))
                .resetTokenExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken))
                .build());
//        cap nhat trong database
//        Token token=tokenService.findByPhonenumber(phoneNumber);
//        token.setAccessToken(accessToken);
//        tokenService.save(token);
        // Trả về LoginResponse chứa access token và refresh token mới
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    @Override
    public String logout(HttpServletRequest request) {
//        Xoa token trong database
        String refreshToken=request.getHeader("x-token");
        if(refreshToken==null || refreshToken.isEmpty()) {
            throw new InvalidParamException("Token must be not blank");
        }
//        lay phonumber tu jwt refreshtoken
        final String phonenumber=jwtTokenUtils.extractPhonenumber(refreshToken,TokenType.REFRESH_TOKEN);
//check token in database
        Token token=tokenService.findByPhonenumber(phonenumber);
//        xoa token trong redis
        redisTokenService.deleteToken(phonenumber);
//        xoa token trong db
//        tokenService.delete(token);
        return "OK";
    }
    @Override
    public String forgotPassword(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email không tồn tại!");
        }

        String code = generate6DigitCode();
        redisTemplate.opsForValue().set("code:"+email, code, 1, TimeUnit.MINUTES); // 1 phút

        emailService.sendVerificationCode(email, code);

        return code;

    }
    private String generate6DigitCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    @Override

    public String resetPassword(String secretKey){
        log.info("-----------------resetPassword----------------------");
        User user=isValidUserByResetToken(secretKey);
        redisTokenService.getTokenById(user.getPhoneNumber());
        return "reset";
    }
    @Override

    public String changePassword(ResetPasswordDTO request){
        User user=isValidUserByResetToken(request.getSecretKey());
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidParamException("Pass word not math");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "changed";
    }

    @Override
    public List<RedisToken> getAllToken() {
        return redisTokenService.getAllToken();
    }

    private User isValidUserByResetToken(String secretKey){
        final String phoneNumber = jwtTokenUtils.extractPhonenumber(secretKey, TokenType.RESET_TOKEN);
        // Tìm người dùng theo số điện thoại
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found with phone number: " + phoneNumber));

        // Kiểm tra xem refresh token có hợp lệ không
        if (!jwtTokenUtils.validateToken(secretKey, user,TokenType.RESET_TOKEN)) {
            throw new InvalidParamException("Refresh token is expired or invalid.");
        }
        return user;
    }

}
