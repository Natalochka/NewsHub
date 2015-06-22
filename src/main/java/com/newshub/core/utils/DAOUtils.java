package com.newshub.core.utils;

import org.logi.crypto.sign.Fingerprint;
import org.logi.crypto.sign.MD5State;

import java.util.List;

/**
 * Created by Natalie on 24.04.2015.
 */
public class DAOUtils {
    public static String encryption(String loginInfo) {
        MD5State md5State = new MD5State();
        md5State.update(loginInfo.getBytes());
        Fingerprint hash = md5State.calculate();
        String encryptedString = hash.toString();
        encryptedString = encryptedString.substring(
                encryptedString.indexOf(",") + 1, encryptedString.length() - 1);
        return encryptedString;
    }

    List<Integer> search(String searchableText) {
        return null;
    }
}