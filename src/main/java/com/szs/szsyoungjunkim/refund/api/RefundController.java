package com.szs.szsyoungjunkim.refund.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refund.api.response.RefundResponse;
import com.szs.szsyoungjunkim.refund.facade.RefundFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "RefundController", description = "사용자 결정세액 조회 API")
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class RefundController {

    private final JwtUtil jwtUtil;
    private final RefundFacade refundFacade;

    @Operation(summary = "결정세액 조회", description = "사용자의 결정세액을 조회할 수 있다",
            security = @SecurityRequirement(name = "BearerAuth"),
            responses = {

                    @ApiResponse(
                            responseCode = "200",
                            description = "결정세액 조회 완료",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 200, \"message\": \"success\", \"data\": {\"결정세액\": \"100,000\"}}"))
                    ),
                    @ApiResponse(responseCode = "400", description = "스크래핑을 먼저 진행하지 않았을 경우(기초 데이터가 없는 경우)",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 400, \"message\": \"스크래핑을 먼저 진행해주세요.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "401", description = "jwt access 토큰이 만료되거나 적절하지 않은 토큰일 경우", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "사용자의 과세표준을 찾지 못하는 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 404, \"message\": \"해당 세율 구간을 찾을 수 없습니다.\", \"data\": null}")))
            })
    @GetMapping("/refund")
    public ApiRes<RefundResponse> refund(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromRequest(request);
        String userId = jwtUtil.getUserId(token);
        return ApiRes.createSuccess(new RefundResponse(refundFacade.getFinalTaxAmount(userId)));
    }
}
