package me.wuzhong.springbootcommons.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author wuzhong on 2018/1/5.
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = new MockHttpSession();
        session.setAttribute("user","1");
    }

    @Test
    public void success() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/hellos/success").
            accept(MediaType.ALL)).andDo(MockMvcResultHandlers.print()).
            andExpect(MockMvcResultMatchers.status().isOk()).
            andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("!!!")));
    }

    @Test
    public void fail() {
    }
}