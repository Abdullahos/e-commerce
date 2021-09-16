package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObjects(Object target, String fieldName, Object toInject){
        boolean canAccess = false;
        try{
            Field field = target.getClass().getDeclaredField(fieldName);
            if(!field.isAccessible()){
                field.setAccessible(true);
                canAccess = true;
            }
            field.set(target,toInject);
            if(canAccess){
                field.setAccessible(false);
            }
        }
        catch (NoSuchFieldException e){

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
