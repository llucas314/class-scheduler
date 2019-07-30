/*
 * File: ExpressionStack.java
 * Name: Lorenzo Lucas
 * Date: 3/10/2019
 * Purpose: Creates an exception when a cycle is detected
 */
package com.lorenzolucas.class_schedule;
public class CycleException extends Exception {

    public CycleException(String message){
        super(message);
    }
}
