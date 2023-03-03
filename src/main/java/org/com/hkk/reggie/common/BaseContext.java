package org.com.hkk.reggie.common;

public class BaseContext {
    //存放用户的id
    private static ThreadLocal<Long> threadLocal= new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
