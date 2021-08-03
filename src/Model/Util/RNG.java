package Model.Util;

import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static boolean RNGsuccess(int max){
        return RNGsuccess(100, max);
    }

    /**
     *
     * @param maxRNGValue if it's 100, the RNG will be between 1 and 100.
     * @param valueToReach If it's 60, will return 60 if the randomly generated number <= 60.
     * @return
     */
    public static boolean RNGsuccess(int maxRNGValue, int valueToReach){
        try{
            return GenerateNumber(1, maxRNGValue) <= valueToReach;
        }catch (Exception e){
            return false;
        }

    }

    public static int GenerateNumber(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
