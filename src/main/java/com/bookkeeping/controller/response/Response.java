package com.bookkeeping.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
    private String flag;
    private String message;
    private T data;


//    public static <T> Response.ResponseBuilder<T> builder() {
//        return new Response.ResponseBuilder();
//    }
//
//
//    public static class ResponseBuilder<T> {
//        private String flag;
//        private String message;
//        private T data;
//
//        ResponseBuilder() {
//        }
//
//        public Response.ResponseBuilder<T> flag(final String flag) {
//            this.flag = flag;
//            return this;
//        }
//
//        public Response.ResponseBuilder<T> message(final String message) {
//            this.message = message;
//            return this;
//        }
//
//        public Response.ResponseBuilder<T> data(final T data) {
//            this.data = data;
//            return this;
//        }
//
//        public Response<T> build() {
//            return new Response(this.flag, this.message, this.data);
//        }
//
//        public String toString() {
//            return "Response.ResponseBuilder(flag=" + this.flag + ", message=" + this.message + ", data=" + this.data + ")";
//        }
//    }

}
