package com.piaoniu.common;

import com.google.common.collect.Maps;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralPair<L,R> {
    L left;
    R right;

    public GeneralPair(L l, R r){
        this.left = l;
        this.right = r;
    }

    public static void main(String[] args) {
        GeneralPair<Integer,Integer> p1 = new GeneralPair<>(1,1);
        GeneralPair<Integer,Integer> p2 = new GeneralPair<>(1,1);
        GeneralPair<Integer,Integer> p3 = new GeneralPair<>();
        p3.setLeft(1);p3.setRight(1);
        Map map = Maps.newHashMap();
        map.put(p1,1);
        map.put(p2,1);
        System.out.println(p1.equals(p3));
        System.out.println(map.get(p3));
        map.forEach((k,v)->System.out.println(k.toString()+v.toString()));
    }
}
