package com.app.network;


public interface NewRequestListener<T> {

    void onSuccess(T data);

    void onFail(String message, int code);
}
