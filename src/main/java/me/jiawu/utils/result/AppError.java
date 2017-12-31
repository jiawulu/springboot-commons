package me.jiawu.utils.result;

import java.io.Serializable;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
public class AppError implements Serializable {

    private String description;
    private int code;

    public AppError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public AppException toException() {
        return new AppException(this);
    }

    public AppException toException(String message) {
        return new AppException(this, message);
    }

    public AppException raise() {
        throw this.toException();
    }

    public AppException raise(String message) {
        throw this.toException(message);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof AppError) {
            return ((AppError)obj).getCode() == this.getCode();
        } else {
            return false;
        }
    }

}
