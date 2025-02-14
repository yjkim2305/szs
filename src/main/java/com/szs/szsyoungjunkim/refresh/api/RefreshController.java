package com.szs.szsyoungjunkim.refresh.api;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import com.szs.szsyoungjunkim.refresh.api.response.RefreshReissueResponse;
import com.szs.szsyoungjunkim.refresh.application.dto.RefreshReissueDto;
import com.szs.szsyoungjunkim.refresh.application.service.RefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "RefreshController", description = "refreshToken으로 accessToken 재발급 API(로그인 후 accessToken이 만료될 시 재발급할 때 진행)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/szs")
public class RefreshController {
    private final RefreshService refreshService;

    @Operation(summary = "accessToken 재발급", description = "accessToken 만료 시 쿠키에 저장된 refreshToken으로 재발급",
            responses = {
                    @ApiResponse(responseCode = "200", description = "accessToken 재발급 성공(accessToken: API 요청 시 인증, refreshToken: accessToken 재발급할 때 사용)",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\n" +
                                            "  \"status\": 200,\n" +
                                            "  \"message\": \"success\",\n" +
                                            "  \"data\": {\n" +
                                            "    \"accessToken\": \"accessTokenContent\",\n" +
                                            "    \"refreshToken\": \"refreshTokenContent\"\n" +
                                            "  }\n" +
                                            "}"))),
                    @ApiResponse(responseCode = "400", description = "로그인을 통해 refreshToken이 발급되지 않은 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 400, \"message\": \"refreshToken이 존재하지 않습니다.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "401", description = "refreshToken이 만료된 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 401, \"message\": \"refreshToken이 만료되었습니다.\", \"data\": null}"))),
                    @ApiResponse(responseCode = "401", description = "적합하지 않은 refreshToken일 경우",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{\"status\": 401, \"message\": \"적합하지 않은 refreshToken 입니다.\", \"data\": null}")))
            })
    @PostMapping("/reissue")
    public ApiRes<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        RefreshReissueDto refreshReissueDto = refreshService.reissueToken(request);
        response.addCookie(refreshService.createRefreshCookie(refreshReissueDto.refreshToken()));
        return ApiRes.createSuccess(RefreshReissueResponse.from(refreshReissueDto));
    }
}
