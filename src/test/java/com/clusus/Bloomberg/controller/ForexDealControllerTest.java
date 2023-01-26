package com.clusus.Bloomberg.controller;

import com.clusus.Bloomberg.service.ForexDealService;
import com.clusus.Bloomberg.util.APIConstant;
import com.clusus.Bloomberg.util.MessageConstant;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ForexDealController.class)
public class ForexDealControllerTest {

    private final Logger LOGGER = LoggerFactory.getLogger(ForexDealControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ForexDealService forexDealService;

    private final String dealEndPoint = APIConstant.API_PATH + APIConstant.UPLOAD;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void fileUploadTest() throws IOException {
        LOGGER.debug("fileUploadTest()");
        String fileName = "file.csv";
        Mockito.doReturn(MessageConstant.UPLOAD_SUCCESS + fileName).when(forexDealService).uploadFile(any());
        RestAssuredMockMvc
                .given()
                .multiPart("file",
                        fileName,
                        new ClassPathResource(fileName).getInputStream(), "text/csv")
                .when().post(dealEndPoint)
                .then()
                .assertThat()
                .statusCode(200).
                expect(jsonPath("$.message", is(MessageConstant.UPLOAD_SUCCESS + fileName)));
    }
}
