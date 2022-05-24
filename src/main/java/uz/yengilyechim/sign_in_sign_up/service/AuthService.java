package uz.yengilyechim.sign_in_sign_up.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.yengilyechim.sign_in_sign_up.entity.User;
import uz.yengilyechim.sign_in_sign_up.exception.RestException;
import uz.yengilyechim.sign_in_sign_up.payload.ApiResult;
import uz.yengilyechim.sign_in_sign_up.payload.LoginDto;
import uz.yengilyechim.sign_in_sign_up.payload.SignUpDto;
import uz.yengilyechim.sign_in_sign_up.payload.TokenDto;
import uz.yengilyechim.sign_in_sign_up.repository.RoleRepository;
import uz.yengilyechim.sign_in_sign_up.repository.UserRepository;
import uz.yengilyechim.sign_in_sign_up.security.JwtTokenProvider;
import uz.yengilyechim.sign_in_sign_up.utils.AppConstant;

import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;//tokenda kelgan userni bazada bor yo'qligini tekshiriberadi
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public ApiResult<?>  signIn(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken/*avtamatik loadUserByUsername() metitini ishlatib bazadan shu usernamelik user bormi deb teshkiryapti, bu user sistemaga kirmoqchi bo'lgan hol uchun */
                    (
                            loginDto.getUsername(),//Object principal
                            loginDto.getPassword() //Object credentials
                    ));
            User user = (User) authentication.getPrincipal();
            String refreshToken = jwtTokenProvider.generateTokenFromId(user.getId(), false);
            String accessToken = jwtTokenProvider.generateTokenFromId(user.getId(), true);
            return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
        }catch (BadCredentialsException e){
            return ApiResult.errorResponse("Login yoki parol hato");
        }
    }
    public ApiResult<?> signUp(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) return ApiResult.errorResponse("bu nomli username bazada mavjud");
        User user = new User(
                signUpDto.getUsername(),
                passwordEncoder.encode(signUpDto.getPassword()),
                roleRepository.findByName(AppConstant.USER).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"role mavjudmas")),
                true
        );
        userRepository.save(user);
        String accessToken = jwtTokenProvider.generateTokenFromId(user.getId(), true);
        String refreshToken = jwtTokenProvider.generateTokenFromId(user.getId(), false);
        return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));

    }


    public ApiResult<TokenDto> refreshToken(TokenDto tokenDto) {
        try {
            jwtTokenProvider.validToken(tokenDto.getAccessToken());
            return ApiResult.successResponse(tokenDto);
        } catch (ExpiredJwtException e) {
            try {
                UUID userId = UUID.fromString(jwtTokenProvider.getIdFromToken(tokenDto.getRefreshToken()));
                return ApiResult.successResponse(new TokenDto(
                        jwtTokenProvider.generateTokenFromId(userId, true),
                        jwtTokenProvider.generateTokenFromId(userId, false)
                ));
            } catch (Exception ex) {
                throw new RestException(HttpStatus.UNAUTHORIZED, "Refresh token buzligan");
            }

        } catch (Exception e) {
            throw new RestException(HttpStatus.UNAUTHORIZED, "Access token buzligan");
        }
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserDetails loadById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));
    }



}
