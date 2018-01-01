package me.jiawu.guava;

import com.google.common.base.Preconditions;
import org.junit.Test;

/**
 * @author wuzhong on 2018/1/1.
 * @version 1.0
 */
public class PreconditionsTes {

    @Test
    public void test(){

        Preconditions.checkArgument(3 < 2 , "message %s" , "123");

    }

}
