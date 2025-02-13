package com.szs.szsyoungjunkim.refund.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refund.api.response.RefundResponse;
import com.szs.szsyoungjunkim.refund.facade.RefundFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class RefundController {

    private final JwtUtil jwtUtil;
    private final RefundFacade refundFacade;

    @Operation(summary = "결정세액 조회", description = "사용자의 결정세액을 조회할 수 있다",
            security = @SecurityRequirement(name = "BearerAuth"))
    @GetMapping("/refund")
    public ApiRes<RefundResponse> refund(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromRequest(request);
        String userId = jwtUtil.getUserId(token);
        return ApiRes.createSuccess(new RefundResponse(refundFacade.getFinalTaxAmount(userId)));
    }
}
