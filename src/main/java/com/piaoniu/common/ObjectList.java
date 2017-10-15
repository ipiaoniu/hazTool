package com.piaoniu.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by slj on 17/5/16.
 */
public class ObjectList<T> extends ArrayList<T> {

    public ObjectList(Collection<? extends T> c) {
        super(c);
    }

    public ObjectList() {
    }

    public static ObjectList of(List<Object> a){
        return new ObjectList(a);
    }


}
