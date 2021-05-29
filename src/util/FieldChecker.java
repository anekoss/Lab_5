package util;

import exceptions.IncorrectFieldException;

public interface FieldChecker<T> {
    T check(String str) throws IncorrectFieldException;
}