package me.jiawu.commons.query;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
@Data
public class BaseQuery implements Serializable{

    private static final long serialVersionUID = 5462586876888016585L;
    protected Integer pageSize;
    protected Integer pageNum;
    protected String orderByClause;

}
