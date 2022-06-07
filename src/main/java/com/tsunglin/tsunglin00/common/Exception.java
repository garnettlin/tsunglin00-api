package com.tsunglin.tsunglin00.common;

public class Exception extends RuntimeException {

    public Exception() {
    }

    public Exception(String message) {
        super(message);
    }

    /**
     * 丟出一個異常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new Exception(message);
    }

}
