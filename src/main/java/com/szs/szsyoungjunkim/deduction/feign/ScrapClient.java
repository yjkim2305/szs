package com.szs.szsyoungjunkim.deduction.feign;

import com.szs.szsyoungjunkim.common.config.OpenFeignConfig;
import com.szs.szsyoungjunkim.deduction.feign.request.ScrapRequest;
import com.szs.szsyoungjunkim.deduction.feign.response.ClientApiRes;
import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "scrapClient",
        url = "${scrap.url}",
        configuration = OpenFeignConfig.class
)
public interface ScrapClient {
    @PostMapping("/scrap")
    ClientApiRes<ScrapResponse> scrapData(@Valid @RequestBody ScrapRequest rq);
}
