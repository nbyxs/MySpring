package com.example.myspring;

import com.example.myspring.exception.requestException;
import com.example.myspring.mvc.RequestScanner;

public class CStart {
    public static void main(String[] args) throws requestException {
        RequestScanner.init();
    }
}
