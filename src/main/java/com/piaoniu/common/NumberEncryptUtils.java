package com.piaoniu.common;

public class NumberEncryptUtils {

    private static final int[] RANDOM_SEEDS = {0,60,91,150,114,145,21,54,68,56,141,241,148,183,29,239,83,118,234,186,170,41,132,177,226,28,82,149,63,2,159,158,136,1,65,169,69,33,250,213,221,255,230,30,252,47,107,105,133,146,22,96,62,248,237,254,197,6,162,204,137,19,216,246,106,87,243,236,58,8,188,75,206,74,124,139,40,152,92,218,50,209,161,99,192,251,210,100,35,36,59,130,143,147,138,202,163,90,45,211,93,12,73,53,51,222,129,4,233,13,189,115,196,121,42,27,142,166,116,125,203,238,178,17,194,34,134,79,249,184,167,200,123,235,201,151,84,165,104,5,187,97,191,217,153,85,244,7,18,71,223,10,15,103,198,98,208,88,101,108,126,72,81,205,38,219,127,253,144,180,43,199,175,135,231,78,154,3,173,67,24,228,212,172,232,113,70,215,80,214,174,23,156,122,242,155,164,9,182,55,190,131,207,110,25,224,120,48,94,39,44,32,109,26,49,111,140,176,225,119,46,193,157,64,57,117,160,247,181,66,16,20,52,220,11,95,185,102,37,61,77,89,229,31,240,171,76,86,168,112,195,14,227,128,245,179};
    private static final int[] REVERSE_SEEDS= new int[RANDOM_SEEDS.length];
    static {
        for (int i=1;i<RANDOM_SEEDS.length;i++){
            REVERSE_SEEDS[RANDOM_SEEDS[i]] = i;
        }
    }

    static int shade = 0x000000FF;
    public static int encrypt(int origin){
        return encrypt(origin,REVERSE_SEEDS);
    }

    public static int encrypt(int origin, int[] seeds){
        int left = origin;
        int cur = 0;
        int offset = 0;
        while(left != 0){
            int t = left&shade;
            cur = (seeds[t]<<offset) | cur;
            offset+=8;
            left = left>>8;
        }
        return cur;
    }

    public static int decrypt(int current){
        return encrypt(current,RANDOM_SEEDS);
    }

    //public static void main(String[] args) {
    //    long now = System.currentTimeMillis();
    //    Map<Integer,Integer> set = Maps.newHashMap();
    //    for (int i=1;i<100000;i++){
    //        int t = encrypt(i);
    //        System.out.println(t+"\t"+i);
    //        if(set.containsKey(t)){
    //            System.out.println(t+"\t"+i+"\t"+set.get(t));
    //            throw new FatalError("sdf");
    //        }else {
    //            set.put(t,i);
    //        }
    //        if (i != decrypt(t)){
    //            System.out.println((11000000+t)+"\t"+i);
    //            throw new FatalError("sdf");
    //        }
    //    }
    //    System.out.println("cost"+(System.currentTimeMillis()-now));
    //}

    //public static void main(String[] args) {
    //    int max = (1<<8)-1;
    //    Random random = new Random();
    //    int[] numbers = new int[max];
    //    for (int i = 0;i<max;i++)
    //        numbers[i] = i+1;

    //    for (int i = 0;i<max;i++){
    //        int target = random.nextInt(max-i);
    //        swap(numbers, i, target + i);
    //    }


    //    System.out.print(max);
    //    System.out.print("{");

    //    for (int i = 0;i<max;i++){
    //        System.out.print(numbers[i]);
    //        if (i!=max-1)
    //            System.out.print(",");
    //    }
    //    System.out.print("}");

    //}

    //private static void swap(int[] numbers, int i, int i1) {
    //    int t=numbers[i];
    //    numbers[i] = numbers[i1];
    //    numbers[i1] = t;
    //}

}
