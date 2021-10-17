package sample.samplespring.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import sample.samplespring.util.CommonConstant;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
//@WebMvcTest(AuditRestController.class)
//@Transactional
public class AuditRestControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Before
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }


    @Test
    public void 존재하지_않는_url로_요청할경우() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/audit/type/info/idx//"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void 인덱스이용_감사타입정보_조회_테스트() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/audit/type/info/idx/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.auditTypeIdx").value(6));
    }


    @Test
    public void 인덱스_입력안할경우_감사타입정보_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/info/idx/"))
                .andExpect(status().isNotFound());
        // todo : not found error formatting is required
    }


    @Test
    public void 인덱스를_숫자가_아닌값으로_입력한경우_감사타입정보_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/info/idx/o"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void 서비스명_타입명_이용_감사타입정보_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/info/service_type/dmpmanager/FILEDOWNLOAD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.type").value("FILEDOWNLOAD"))
                .andExpect(jsonPath("$.data.service").value("dmpmanager"));
    }


    @Test
    public void 존재하지않는_서비스명_감사타입정보_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/info/service_type/wrongservice/FILEDOWNLOAD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(CommonConstant.NONE_TARGET_ERROR_MSG));
    }


    @Test
    public void 감사타입정보_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(6)));
    }


    @Test
    public void 서비스명_이용_감사타입정보_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/list/service/dmpmanager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }


    @Test
    public void 존재하지않는_서비스명_이용_감사타입정보_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/list/service/wrongmanager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(CommonConstant.NONE_TARGET_ERROR_MSG));
    }


    @Test
    public void 타입명_이용_감사타입정보_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/list/type/FILEDOWNLOAD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }


    @Test
    public void 존재하지않는_타입명_이용_감사타입정보_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/type/list/type/wrongtype"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(CommonConstant.NONE_TARGET_ERROR_MSG));
    }


    @Test
    public void 감사로그_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/log/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(67)));
    }

    @Test
    public void 서비스명_타입명_이메일_로그일자_이용_감사로그_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/log/list/service/audmix/type/FILECREATE/email/dmp@onestorecorp.com/logdate/2020-06-26"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].service").value("audmix"))
                .andExpect(jsonPath("$.data[0].type").value("FILECREATE"))
                .andExpect(jsonPath("$.data[0].email").value("dmp@onestorecorp.com"))
                .andExpect(jsonPath("$.data[0].eventDate").value("2020-06-26T02:44:16.000+00:00"));
    }


    @Test
    public void 로그일자_서비스명_이용_감사로그_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/log/list/logdate/2020-06-26/service/audmix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }


    @Test
    public void 유효하지않은_로그일자_이용_로그_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/audit/log/list/logdate/20200626/service/audmix"))
                .andExpect(status().isBadRequest());
    }
}