package com.app.network.firebase;


public interface CustomRequestListener<T> {

    void onSuccess(int totalPages, T data);

    void onFail(String message);
}
