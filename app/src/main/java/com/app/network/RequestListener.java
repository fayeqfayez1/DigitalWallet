package com.app.network;


public interface RequestListener<T> {

    void onSuccess(T data);

    void onFail(String message);
}
