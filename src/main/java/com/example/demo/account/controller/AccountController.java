package com.example.demo.account.controller;

import com.example.demo.account.controller.form.*;
import com.example.demo.account.controller.form.business.BusinessAccountRegisterForm;
import com.example.demo.account.controller.form.business.BusinessCheckRequestForm;
import com.example.demo.account.controller.form.normal.NormalAccountRegisterForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantReadResponseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;

    // 개인 회원 회원 가입
    @PostMapping("/normal-register")
    public Boolean normalAccountRegister (@RequestBody NormalAccountRegisterForm registerForm) {
        return accountService.normalAccountRegister(registerForm.toAccountRegisterRequest());
    }

    // 사업자 회원 회원 가입
    @PostMapping("/business-register")
    public Boolean businessAccountRegister (@RequestBody BusinessAccountRegisterForm requestForm) {
        return accountService.businessAccountRegister(requestForm.toAccountRegisterRequest());
    }

    // 이메일 중복체크
    @GetMapping("/check-email/{email}")
    public Boolean checkEmail (@PathVariable("email") String email) {
        log.info("ckeckEmail: " + email);

        return accountService.checkEmailDuplication(email);
    }

    // 로그인
    @PostMapping("/login")
    public String accountLogin (@RequestBody AccountLoginRequestForm accountLoginRequestForm){

        String userToken = accountService.login(accountLoginRequestForm);

        return userToken;
    }

    // 사업자 회원 확인
    @PostMapping("/businessCheck")
    public Boolean isBusiness(@RequestBody BusinessCheckRequestForm requestForm){

        String userToken = requestForm.getUserToken();
        Long businessId = accountService.findAccountId(userToken);
        log.info("businessId: "+businessId);

        if (businessId==null){
            return false;
        }

        Boolean isBusinessMan= accountService.businessCheck(businessId);
        return isBusinessMan;
    }

    // 이메일 반환
    @PostMapping("/return-email")
    public String returnEmail(@RequestBody ReturnEmailRequestForm requestForm) {
        log.info("returnEmail()");

        String userToken = requestForm.getUserToken();
        String returnEmail = accountService.returnEmail(userToken);
        log.info("returnEmail: " + returnEmail);

        return returnEmail;
    }

    // accountId 반환
    @PostMapping("/return-accountId")
    public Long returnAccountId(@RequestBody ReturnAccountIdRequestForm requestForm) {
        log.info("returnAccountId()");

        String userToken = requestForm.getUserToken();
        Long returnAccountId = accountService.findAccountId(userToken);
        log.info("returnEmail: " + returnAccountId);

        return returnAccountId;
    }

    // 이메일 인증
    @ResponseBody
    @PostMapping("mail-confirm/{email}")
    String mailConfirm(@PathVariable("email") String email) throws Exception {

        String code = accountService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    // 회원 정보
    @PostMapping("/give-info")
    public Account readAccount (@RequestBody AccountReadRequestForm requestForm) {
        log.info("readAccount()");

        return accountService.read(requestForm);
    }
}
