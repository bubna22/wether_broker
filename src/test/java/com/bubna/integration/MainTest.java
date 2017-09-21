package com.bubna.integration;

import com.bubna.WebConfig;
import com.bubna.controller.DefaultController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class MainTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean(DefaultController.class));
    }

    @Test
    public void testDefaultControllerGetRequest() throws Exception {
        this.mockMvc
                .perform(get("/update/{name}", "Engels"))
                .andDo(print()).andExpect(status().isOk());
        this.mockMvc
                .perform(get("/update/{name}", "BuRGas"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testDefaultRestControllerGetRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/get/{town_name}", "Engels"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.query.results.channel.location.city").value("engels"))
                .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());

        mvcResult = this.mockMvc.perform(get("/get/{town_name}", "SaraToV"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.query.results.channel.location.city").value("saratov"))
                .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

}
