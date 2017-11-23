package com.company.core.biz;

import com.company.core.util.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomCredentialsMatcherBiz {
    
    public String encrypt(String data) {
        data = EncryptUtils.encryptMD5(data);
        String sha384Hex = new Sha384Hash(data).toBase64();
        System.out.println(data + ":" + sha384Hex);
        return sha384Hex;
    }
    
}
