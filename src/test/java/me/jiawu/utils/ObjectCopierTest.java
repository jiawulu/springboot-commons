package me.jiawu.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
public class ObjectCopierTest {

    @Test
    public void copyProperties() {

        B b  = new B();
        b.setBb("bb");
        b.setName("name");
        b.setAge(10L);

        C c = new C();
        c.setB(b);

        B b2 = new B();

        C c2 = new C();
        ObjectCopier.copyProperties(c2, c);

        Assert.assertTrue(10L == c2.getB().getAge());

    }



    @Test
    public void copyProperties2() {


        B b  = new B();
        b.setBb("bb");
        b.setName("name");
        b.setAge(10L);

        B b2 = new B();
        ObjectCopier.copyProperties(b2, b);

        Assert.assertTrue(10L == b2.getAge());

    }

    @Test
    public void copyProperties3() {

        B b  = new B();
        b.setBb("bb");
        b.setName("name");
        b.setAge(10L);

        B b2 = new B();
        ObjectCopier.copyPropertiesSpring(b2, b);

        Assert.assertTrue(10L == b2.getAge());

    }

    @Test
    public void from() {
        B b  = new B();
        b.setBb("bb");
        b.setName("name");
        b.setAge(10L);

        B from = ObjectCopier.from(b, B.class);

        Assert.assertTrue(10L == from.getAge());

        A a = ObjectCopier.from(b,A.class);

        Assert.assertTrue(10L == a.getAge());

    }

    @Test
    public void from1() {
    }

    @Test
    public void copyNonAware() {

        B b  = new B();
        b.setBb("bb");
        b.setName("name");
        b.setAge(10L);

        B b2 = new B();
        ObjectCopier.copyPropertiesNonAware(b2, b);

        A a = new A();
        ObjectCopier.copyPropertiesNonAware(a, b);

        Assert.assertTrue(10L == b2.getAge());
        Assert.assertTrue(10L == a.getAge());

    }


    public static class A {
        private String name;
        private Long age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getAge() {
            return age;
        }

        public void setAge(Long age) {
            this.age = age;
        }
    }

    public static class B extends A {
        private String bb;

        public String getBb() {
            return bb;
        }

        public void setBb(String bb) {
            this.bb = bb;
        }
    }

    public static class C {

        private String c;
        private B b;

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }


}