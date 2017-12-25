package me.jiawu.commons.result;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
public class Result<T> implements Serializable {
    private T result;
    private AppError error = null;
    private Integer pageSize;
    private Long offset;
    private Long totalCount;
    private String message;

    private Result() {
    }

    public boolean isSuccess() {
        return this.error == null;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public AppError getError() {
        return this.error;
    }

    public void setError(AppError error) {
        this.error = error;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getOffset() {
        return this.offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result();
        result.setResult(data);
        return result;
    }

    public static <T> Result<T> err(AppError error) {
        Result<T> result = new Result();
        result.setError(error);
        return result;
    }

    public static <T> Result<T> err(AppError error, String message) {
        Result<T> result = err(error);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<List<T>> okPaged(List<T> data, long offset, int pageSize, long totalCount) {
        Result<List<T>> result = new Result();
        result.setResult(data);
        result.setOffset(offset);
        result.setPageSize(pageSize);
        result.setTotalCount(totalCount);
        return result;
    }

    public <M> Result<M> map(Function<T, M> transform) {
        return this.isSuccess() ? ok(transform.apply(this.getResult())) : err(this.getError(), this.getMessage());
    }

    public T unwrap() {
        if (this.isSuccess()) {
            return this.result;
        } else {
            throw this.error.toException(this.message);
        }
    }

    @Override
    public String toString() {
        return "Result{result=" + this.result + ", error=" + this.error + ", pageSize=" + this.pageSize + ", offset="
            + this.offset + ", totalCount=" + this.totalCount + ", message='" + this.message + '\'' + '}';
    }
}

