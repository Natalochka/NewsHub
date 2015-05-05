package com.newshub.core.utils;

import java.util.List;

/**
 * Created by Natalie on 24.04.2015.
 */
public interface DAOUtils {
    String encryption (String loginInfo);
    String decryption (String encryptedLoginInfo);
    List<Integer> search (String searchableText);
}
