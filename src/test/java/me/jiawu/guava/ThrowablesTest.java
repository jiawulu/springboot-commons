package me.jiawu.guava;

import com.google.common.base.Throwables;
import org.apache.ibatis.javassist.bytecode.CodeAttribute.RuntimeCopyException;
import org.junit.Test;

/**
 * @author wuzhong on 2018/1/1.
 * @version 1.0
 */
public class ThrowablesTest {

    @Test
    public void test(){

        Throwable e = new RuntimeException("123", new ClassCastException());

        Throwable rootCause = Throwables.getRootCause(e);

        String stackTraceAsString = Throwables.getStackTraceAsString(rootCause);

        System.out.println(stackTraceAsString);

        //Throwables.propagateIfPossible();

    }

}
