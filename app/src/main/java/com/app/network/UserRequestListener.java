package com.app.network;


public interface UserRequestListener<T> {

    void onSuccess(int action, T data);

    void onFail(String message);
}
