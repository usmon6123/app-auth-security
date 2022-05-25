package uz.yengilyechim.sign_in_sign_up.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yengilyechim.sign_in_sign_up.payload.ApiResult;
import uz.yengilyechim.sign_in_sign_up.payload.LoginDto;
import uz.yengilyechim.sign_in_sign_up.payload.SignUpDto;
import uz.yengilyechim.sign_in_sign_up.payload.TokenDto;
import uz.yengilyechim.sign_in_sign_up.service.AuthService;
import uz.yengilyechim.sign_in_sign_up.utils.RestConstant;

import javax.validation.Valid;

@RestController
@RequestMapping(RestConstant.AUTH_CONTROLLER)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResult<?> signUp (@RequestBody @Valid SignUpDto signUpDto){
        return authService.signUp(signUpDto);
    }

    @PostMapping("/sign-in")
    public ApiResult<?> signIn(@RequestBody @Valid LoginDto loginDto){

        return authService.signIn(loginDto);
    }


    @PostMapping("/refresh-token")
    public ApiResult<TokenDto> refreshToken(@RequestBody TokenDto tokenDto){
        return authService.refreshToken(tokenDto);
    };

    @GetMapping("/get")
    public HttpEntity<?>get(){
        return ResponseEntity.ok("get yo'li ");
    }

    @GetMapping("/get-all")
    public HttpEntity<?>getAll(){
        return ResponseEntity.ok("get yo'li ");
    }

}
