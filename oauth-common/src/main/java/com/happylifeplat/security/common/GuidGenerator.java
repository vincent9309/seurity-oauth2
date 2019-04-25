package com.happylifeplat.security.common;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

import java.util.UUID;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/18 14:54</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class GuidGenerator {
    private static RandomValueStringGenerator defaultClientSecretGenerator = new RandomValueStringGenerator(32);

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String generateClientSecret() {
        return defaultClientSecretGenerator.generate();
    }

    public static void main(String[] args){
        String secret = GuidGenerator.generateClientSecret();
        System.out.println(secret);
        String uid = GuidGenerator.generate();
        System.out.println(uid);
    }
}
