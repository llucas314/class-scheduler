/*
 * File: NameFormatException.java
 * Name: Lorenzo Lucas
 * Date: 3/10/2019
 * Purpose: Creates an exception for inputting the wrong class name
 */
package com.lorenzolucas.class_schedule;
public class NameFormatException extends Exception{

    public NameFormatException(String message){
        super(message);
    }
}
