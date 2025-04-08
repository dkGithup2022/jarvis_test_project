package com.jarvis.sample.simpleboard;

import java.lang.reflect.Field;

public class FakeSetter {
    public static void setField(Object targetObject, String fieldName, Object value) {
        try {
            Field field = targetObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, value);
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            throw new RuntimeException();
        }
    }


    public static void setParentField(Object targetObject, String fieldName, Object value) {
        try {
            Field field = targetObject.getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, value);
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            throw new RuntimeException();
        }
    }
}

