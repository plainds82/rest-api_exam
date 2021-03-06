package com.rest.api.controller.v1;


import com.rest.api.advice.exception.CUserNotFoundException;
import com.rest.api.entity.User;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.repository.UserJpaRepository;
import com.rest.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("hasRole('ROLE_ADMIN')") // 또는 @Secured("ROLE_USER") 으로 권한 설정 가능. 메소드별 설정도 가능. 여기다가 권한 설정 하면 SecurityConfiguration.configure 의 exceptionHandling 이 안됨...
@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepository userJpaRepository;
    private final ResponseService responseService;      // 결과를 처리할 service

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {

//        return responseService.getListResult(userJpaRepository.findAll());
        return responseService.getListResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId 로 회원을 조회한다.")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById(
            @ApiParam(value = "회원ID", required = true) @PathVariable Long msrl,
            @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang
    ) throws Exception {
        // 결과 데이터가 단일건인 경우 getSingleResult 를 이용해서 결과를 출력한다.
//        return responseService.getSingleResult(userJpaRepository.findById(msrl).orElse(null));
        return responseService.getSingleResult(userJpaRepository.findById(msrl).orElseThrow(CUserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다.")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {

        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepository.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다.")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
        userJpaRepository.deleteById(msrl);

        return responseService.getSuccessResult();
    }


    //    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
//    @PostMapping(value = "/user")
//    public User save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
//                     @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
//        User user = User.builder()
//                .uid(uid)
//                .name(name)
//                .build();
//        return userJpaRepository.save(user);
//    }
}
