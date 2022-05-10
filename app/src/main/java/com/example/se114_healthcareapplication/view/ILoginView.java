package com.example.se114_healthcareapplication.view;

public interface ILoginView {
    void onClearText();
    void onLoginResult(Boolean result, int code);
    void onSetProgressBarVisibility(int visibility);
}
