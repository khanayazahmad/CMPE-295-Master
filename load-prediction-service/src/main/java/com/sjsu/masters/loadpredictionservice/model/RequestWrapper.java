package com.sjsu.masters.loadpredictionservice.model;

public class RequestWrapper<T> {

    T body;

    public RequestWrapper(T body) {
        this.body = body;
    }

    public RequestWrapper() {
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static <T> RequestWrapper<T> wrap(T body){
        return new RequestWrapper<>(body);
    }

}
