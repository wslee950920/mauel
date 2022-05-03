package com.mauel.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sex {
    MALE('M'), FEMALE('F');

    private final char head;

    public static Sex valueOf(int value){
        switch (value){
            case 1:
            case 3:
            case 9:
            case 5:
                return MALE;
            case 2:
            case 4:
            case 0:
            case 8:
                return FEMALE;
            default:
                throw new AssertionError("Unknown value : "+value);
        }
    }

    public static Sex valueOf(char value){
        switch (value){
            case 'M':
                return MALE;
            case 'F':
                return FEMALE;
            default:
                throw new AssertionError("Unknown value : "+value);
        }
    }
}
