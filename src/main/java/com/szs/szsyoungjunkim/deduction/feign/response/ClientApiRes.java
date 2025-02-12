package com.szs.szsyoungjunkim.deduction.feign.response;

import lombok.Getter;

@Getter
public class ClientApiRes<T> {

    private String status;
    private T data;
    private Error error;

    public static class Error {
        private String code;
        private String message;
        private String validations;
    }
}
