package com.app.network;


public interface MultipleRequestListener<T,F> {

    void onSuccess(T data, F data1);

    void onFail(String message);
}
