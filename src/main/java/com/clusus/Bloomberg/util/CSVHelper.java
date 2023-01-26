package com.clusus.Bloomberg.util;

import com.clusus.Bloomberg.dto.ForexDealDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static com.clusus.Bloomberg.util.DateTimeUtil.convertStringToLocalDateTime;
import static com.clusus.Bloomberg.util.MessageConstant.*;

public class CSVHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVHelper.class);

    private CSVHelper(){
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<ForexDealDTO> csvToDeals(InputStream inputStream, String fileName) {
        LOGGER.debug("csvToDeals() with fileName: {}", fileName);
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<ForexDealDTO> dealDTOs = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            LOGGER.debug("csvToDeals() looping the records in csv");
            for (CSVRecord csvRecord : csvRecords) {
                ForexDealDTO dealDTO = new ForexDealDTO();
                checkAndSet(csvRecord.get(Header.DEAL_UNIQUE_ID.getHeader()), Header.DEAL_UNIQUE_ID.getHeader(), dealDTO::setId);
                checkLengthAndSet(csvRecord.get(Header.FROM_ISO_CURRENCY_CODE.getHeader()), Header.FROM_ISO_CURRENCY_CODE.getHeader(), dealDTO::setFromCurrencyISOCode);
                checkLengthAndSet(csvRecord.get(Header.TO_ISO_CURRENCY_CODE.getHeader()), Header.TO_ISO_CURRENCY_CODE.getHeader(), dealDTO::setToCurrencyISOCode);
                withValidDate(csvRecord.get(Header.DEAL_TIME_STAMP.getHeader()), Header.DEAL_TIME_STAMP.getHeader(), dealDTO::setDealTime);
                checkAndSet(csvRecord.get(Header.DEAL_AMOUNT.getHeader()), Header.DEAL_AMOUNT.getHeader(), dealDTO::setDealAmount);
                if (!isInvalid(dealDTO)) {
                    dealDTOs.add(dealDTO);
                }
            }
            return dealDTOs;
        } catch (IOException e) {
            LOGGER.warn("csvToDeals() : FILE_PARSE_FAILED + e.getMessage()");
            throw new RuntimeException(FILE_PARSE_FAILED + e.getMessage());
        }
    }

    /*
     * Check whether fields are empty or not.
     * If the fields are empty, errors are not thrown. they are logged in a file mentioning the invalid value and reason
     * */
    private static void checkAndSet(String value, String headerName, Consumer<String> consumer) {
        if (value == null) {
            LOGGER.warn("Field {} is required", headerName);
        } else {
            consumer.accept(value);
        }
    }

    private static void withValidDate(String value, String headerName, Consumer<LocalDateTime> consumer) {
        if (value == null) {
            LOGGER.warn("Field {} is required", headerName);
        } else {
            LocalDateTime dateTime = convertStringToLocalDateTime(value);
            consumer.accept(dateTime);
        }
    }

    /*
     * Checking if the currency from iso code or currency to iso code length is 3 or not
     * Length must be 3.
     * Also checks if the value is of type String or not
     * */
    private static void checkLengthAndSet(String value, String headerName, Consumer<String> consumer) {
        if (value == null) {
            LOGGER.warn("Field {} is required", headerName);
        } else if (value.length() != 3) {
            LOGGER.warn("Field {} must be of length 3. Invalid value : {}", headerName, value);
        } else if (!(value instanceof String)) {
            LOGGER.warn("Field {} must be of type String. Invalid value : {}", headerName, value);
        } else {
            consumer.accept(value.toUpperCase(Locale.ROOT));
        }
    }

    /*
     * Checking if any of the required field is missing or not
     * */
    private static boolean isInvalid(ForexDealDTO dealDTO) {
        return dealDTO.getId() == null ||
                dealDTO.getFromCurrencyISOCode() == null ||
                dealDTO.getToCurrencyISOCode() == null ||
                dealDTO.getDealTime() == null ||
                dealDTO.getDealAmount() == null;
    }
}