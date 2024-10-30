package com.constant;

import java.security.SecureRandom;

public class DefaultConstant {

    public static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();

    public static final char[] DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
}
