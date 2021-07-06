package Util;

import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static boolean RNGsuccess(int max){
        return GenerateNumber(1, 100) <= max;
    }

    public static int GenerateNumber(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
