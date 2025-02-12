package com.szs.szsyoungjunkim.deduction.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.deduction.facade.DeductionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class DeductionController {

    private final JwtUtil jwtUtil;
    private final DeductionFacade deductionFacade;

    @Operation(summary = "스크래핑", description = "종합소득금액과 소득공제 스크래핑",
            security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/scrap")
    public ApiRes<?> scrap(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromRequest(request);
        String userId = jwtUtil.getUserId(token);
        deductionFacade.userScrap(userId);
        return ApiRes.createSuccessWithNoContent();
    }

}
