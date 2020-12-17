package com.goldwater.querycenter.common.util.cache;

import com.goldwater.querycenter.entity.management.User;

public class SessionCache {
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static <T extends User> void put(T t) {
        threadLocal.set(t);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get() {
        if(null==(T) threadLocal.get()){
            return (T) new  User();
        }
        return (T) threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
