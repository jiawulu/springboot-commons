package me.jiawu.commons.result;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
public class AppException extends RuntimeException {

    private AppError error;

    AppException(AppError error) {
        this.error = error;
    }

    AppException(AppError error, String message) {
        super(message);
        this.error = error;
    }

    public AppError getError() {
        return this.error;
    }
}
