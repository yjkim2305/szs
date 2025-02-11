package com.szs.szsyoungjunkim.user.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.user.api.request.UserCreateReqest;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/szs")
public class UserController {
    private final UserService userService;

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
}
