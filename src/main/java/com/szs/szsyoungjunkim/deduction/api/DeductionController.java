package com.szs.szsyoungjunkim.deduction.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.deduction.facade.DeductionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DeductionController", description = "사용자 스크래핑 API")
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class DeductionController {

    private final JwtUtil jwtUtil;
    private final DeductionFacade deductionFacade;

    @Operation(summary = "스크래핑", description = "종합소득금액과 소득공제 스크래핑",
            security = @SecurityRequirement(name = "BearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "스크래핑 완료"),
                    @ApiResponse(responseCode = "401", description = "jwt access 토큰이 만료되거나 적절하지 않은 토큰일 경우", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "외부 스크래핑 API 서버 리소스 찾을 수 없을 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 404, \"message\": \"요청한 리소스를 찾을 수 없습니다.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "409", description = "사용자가 스크래핑을 하여 DB에 중복 적재하려고 하는 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 409, \"message\": \"이미 존재하는 스크래핑 데이터입니다.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "500", description = "외부 스크래핑 API 서버 내부 오류 발생했을 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 500, \"message\": \"요청한 서버 내부 오류가 발생했습니다.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "502", description = "외부 스크래핑 API에서 응답 값이 null인 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 502, \"message\": \"스크래핑 응답이 null 입니다.\", \"data\": null}")))
            })
    @PostMapping("/scrap")
    public ApiRes<?> scrap(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromRequest(request);
        String userId = jwtUtil.getUserId(token);
        deductionFacade.userScrap(userId);
        return ApiRes.createSuccessWithNoContent();
    }

}
