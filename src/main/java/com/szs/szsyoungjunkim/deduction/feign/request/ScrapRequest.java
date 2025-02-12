package com.szs.szsyoungjunkim.deduction.feign.request;

public record ScrapRequest(
        String name,
        String regNo
) {
    public static ScrapRequest of(String name, String regNo) {
        return new ScrapRequest(name, regNo);
    }
}
