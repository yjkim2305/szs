package com.szs.szsyoungjunkim.deduction.application.service;

import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeductionService {

    @Transactional
    public void save(ScrapResponse scrapResponse) {


    }
}
