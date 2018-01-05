package me.jiawu.junit;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author wuzhong on 2018/1/5.
 * @version 1.0
 */
public class AssertTest {

   @Test
   public void test(){

       Assert.assertThat(true, is(true));

       Assert.assertThat("1", isOneOf("1","2"));

       Assert.assertThat(Lists.newArrayList("1","2","3"), contains("1", "2","3"));



   }


}
