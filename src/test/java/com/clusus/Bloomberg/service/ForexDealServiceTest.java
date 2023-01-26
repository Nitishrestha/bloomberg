package com.clusus.Bloomberg.service;

import com.clusus.Bloomberg.dto.ForexDealDTO;
import com.clusus.Bloomberg.entity.ForexDeal;
import com.clusus.Bloomberg.exception_handler.InvalidFileFormat;
import com.clusus.Bloomberg.mapper.ForexDealMapper;
import com.clusus.Bloomberg.repository.ForexDealRepository;
import com.clusus.Bloomberg.service.impl.ForexDealServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.clusus.Bloomberg.util.MessageConstant.DATE_AND_TIME_FORMAT;
import static com.clusus.Bloomberg.util.MessageConstant.UPLOAD_SUCCESS;

public class ForexDealServiceTest {

    private final Logger LOGGER = LoggerFactory.getLogger(ForexDealServiceTest.class);

    @InjectMocks
    private ForexDealServiceImpl forexDealService;

    @Mock
    private ForexDealMapper mapper;

    @Mock
    private ForexDealRepository forexDealRepository;

    private ForexDeal forexDeal;
    private ForexDealDTO forexDealDTO;
    private LocalDateTime localDateTime;
    private String fileName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        localDateTime = LocalDateTime.parse("2016-03-04 11:30", DateTimeFormatter.ofPattern(DATE_AND_TIME_FORMAT));
        forexDealDTO = new ForexDealDTO("100", "USD", "HKD", localDateTime, "100");
        forexDeal = new ForexDeal(100, "USD", "HKD", localDateTime, 100D);
        fileName = "file.csv";
        ReflectionTestUtils.setField(forexDealService, "maxFileSize", 4194304L);
    }

    @Test
    public void ShouldUploadCSVFile() throws IOException {
        LOGGER.debug("ShouldUploadCSVFile()");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
                "file.csv",
                "text/csv",
                new ClassPathResource("file.csv").getInputStream());
        Mockito.when(mapper.mapToEntities(List.of(forexDealDTO))).thenReturn(List.of(forexDeal));
        Mockito.when(forexDealRepository.saveAll(List.of(forexDeal))).thenReturn(List.of(forexDeal));
        Assertions.assertEquals(UPLOAD_SUCCESS + fileName, forexDealService.uploadFile(mockMultipartFile));
    }

    @Test
    public void ShouldThrowInvalidFileFormatExceptionWhenFileTypeIsInvalid() throws IOException {
        LOGGER.debug("ShouldThrowExceptionWhenFileTypeIsInvalid()");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
                "sample.txt",
                "text/txt",
                new ClassPathResource("sample.txt").getInputStream());
        Assertions.assertThrows(InvalidFileFormat.class, () -> {
            forexDealService.uploadFile(mockMultipartFile);
        });
    }

    @Test
    public void ShouldThrowExceptionWhenCSVHeadersAreMismatched() throws IOException {
        LOGGER.debug("ShouldThrowExceptionWhenCSVHeadersAreMismatched()");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
                "Invalid.csv",
                "text/csv",
                new ClassPathResource("Invalid.csv").getInputStream());
        Assertions.assertThrows(RuntimeException.class, () -> {
            forexDealService.uploadFile(mockMultipartFile);
        });
    }

    @Test
    public void ShouldThrowExceptionWhenFileSizeIsTooLarge() throws IOException {
        LOGGER.debug("ShouldThrowExceptionWhenFileTypeIsInvalid()");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile",
                "4mb.txt",
                "text/txt",
                new ClassPathResource("4mb.txt").getInputStream());
        Assertions.assertThrows(MaxUploadSizeExceededException.class, () -> {
            forexDealService.uploadFile(mockMultipartFile);
        });
    }
}
