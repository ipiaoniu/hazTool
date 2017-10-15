package com.piaoniu.common;

import java.util.List;
import org.apache.ibatis.session.RowBounds;

/**
 * Created by slj on 16/7/2.
 */
public class PageHelper {

    public static int PAGE_MAX =20;
    public static <T> List<T> pageList(int pageIndex, int pageSize, List<T> datas) {

        int fromIndex = (pageIndex-1)*pageSize;
        if(fromIndex >datas.size()) fromIndex=0;
        if(fromIndex <0) fromIndex=0;
        int toIndex = fromIndex+pageSize;
        if (toIndex>datas.size()) toIndex = datas.size();
        return  datas.subList(fromIndex,toIndex);
    }

    public static RowBounds getRowBounds(int pageIndex, int pageSize){
        int fromIndex = (pageIndex-1)*pageSize;
         return new RowBounds(fromIndex,pageSize>PAGE_MAX?PAGE_MAX:pageSize);
    }
}
