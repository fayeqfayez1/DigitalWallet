package com.app.network;


public interface StringRequestListener<T> {

    void onSuccess(String message, T data);

    void onFail(String message);
}
