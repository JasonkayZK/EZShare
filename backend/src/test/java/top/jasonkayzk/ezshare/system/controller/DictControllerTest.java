package top.jasonkayzk.ezshare.system.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@Disabled
@SpringBootTest
@WebAppConfiguration
class DictControllerTest {

    public static final String pathPrefix = "/system/dict";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getDictList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(pathPrefix)
                .param("pageSize", "5")
                .param("pageNum", "2")
                .accept(MediaType.APPLICATION_JSON))
                // 等同于Assert.assertEquals(200, status);
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 打印出请求和相应的内容
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // 将相应的数据转换为字符串
        String responseString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(responseString);

        Map<String, Object> map = new ObjectMapper().readValue(responseString, new TypeReference<>(){});

        Assertions.assertEquals(200, map.get("code"));

        Assertions.assertEquals(5, ((List)((Map)map.get("data")).get("rows")).size());
    }

    @Test
    void getDict() {
    }

    @Test
    void addDict() {
    }

    @Test
    void updateDict() {
    }

    @Test
    void deleteDicts() {
    }

    @Test
    void export() {
    }
}