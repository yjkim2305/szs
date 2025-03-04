package com.szs.szsyoungjunkim.user.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.user.api.request.UserCreateReqest;
import com.szs.szsyoungjunkim.user.api.request.UserLoginRequest;
import com.szs.szsyoungjunkim.user.api.response.UserLoginResponse;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;
import com.szs.szsyoungjunkim.user.application.service.AuthService;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserController", description = "사용자 회원가입, 로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/szs")
public class UserController {
    private final UserService userService;
    private final UserFacade userFacade;
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "사용자가 회원가입을 한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가입 성공", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "회원가입에 적합하지 않는 회원일 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 400, \"message\": \"회원가입에 적합하지 않는 회원입니다.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "409", description = "이미 존재하는 회원 ID가 있을 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 409, \"message\": \"이미 존재하는 회원입니다.\", \"data\": null}")))
            })
    @PostMapping("/signup")
    public ApiRes<?> signUpUser(@RequestBody UserCreateReqest rq) {
        userService.signUpUser(UserCreateCommand.from(rq));
        return ApiRes.createSuccessWithNoContent();
    }

    @Operation(summary = "로그인", description = "사용자가 로그인을 한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공(accessToken: API 요청 시 인증, refreshToken: accessToken 재발급 할 때 사용)",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\n" +
                                            "  \"status\": 200,\n" +
                                            "  \"message\": \"success\",\n" +
                                            "  \"data\": {\n" +
                                            "    \"accessToken\": \"accessTokenContent\",\n" +
                                            "    \"refreshToken\": \"refreshTokenContent\"\n" +
                                            "  }\n" +
                                            "}"))),
                    @ApiResponse(responseCode = "401", description = "사용자 아이디 또는 패스워드가 적합하지 않을 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 401, \"message\": \"아이디 또는 비밀번호가 잘못되었습니다.\", \"data\": null}"))),
            })
    @PostMapping("/login")
    public ApiRes<UserLoginResponse> loginUser(@RequestBody UserLoginRequest rq, HttpServletResponse response) {
        UserLoginDto userLoginDto = userFacade.login(UserLoginCommand.from(rq));
        response.addCookie(authService.createRefreshCookie(userLoginDto.refreshToken()));
        return ApiRes.createSuccess(UserLoginResponse.of(userLoginDto));
    }
}
