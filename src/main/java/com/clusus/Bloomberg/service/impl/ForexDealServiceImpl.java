package com.clusus.Bloomberg.service.impl;

import com.clusus.Bloomberg.dto.ForexDealDTO;
import com.clusus.Bloomberg.entity.ForexDeal;
import com.clusus.Bloomberg.exception_handler.InvalidFileFormat;
import com.clusus.Bloomberg.mapper.ForexDealMapper;
import com.clusus.Bloomberg.repository.ForexDealRepository;
import com.clusus.Bloomberg.service.ForexDealService;
import com.clusus.Bloomberg.util.CSVHelper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.clusus.Bloomberg.util.MessageConstant.*;

@Service
public class ForexDealServiceImpl implements ForexDealService {

    private final Logger LOGGER = LoggerFactory.getLogger(ForexDealServiceImpl.class);

    @Value("${spring.servlet.multipart.max-file-size}")
    private Long maxFileSize;

    private final ForexDealRepository forexDealRepository;
    private final ForexDealMapper mapper = Mappers.getMapper(ForexDealMapper.class);


    public ForexDealServiceImpl(ForexDealRepository forexDealRepository) {
        this.forexDealRepository = forexDealRepository;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        LOGGER.debug("uploadFile(): {}", file.getOriginalFilename());
        if (file.getSize() > maxFileSize) {
            LOGGER.debug("uploadFile(): file size exceeded {}", file.getOriginalFilename());
            throw new MaxUploadSizeExceededException(maxFileSize);
        }
        if (!CSVHelper.hasCSVFormat(file)) {
            LOGGER.debug("uploadFile(): expected file format is csv {}", file.getOriginalFilename());
            throw new InvalidFileFormat(INVALID_FILE_FORMAT + " Given file: " + file.getOriginalFilename() + "!");
        }
        try {
            List<ForexDealDTO> dealDTOs = CSVHelper.csvToDeals(file.getInputStream(), file.getOriginalFilename());
            List<ForexDeal> deals = mapper.mapToEntities(dealDTOs);
            forexDealRepository.saveAll(deals);
            LOGGER.debug("uploadFile(): successfully {}", file.getOriginalFilename());
            return UPLOAD_SUCCESS + file.getOriginalFilename();
        } catch (IOException e) {
            LOGGER.warn("uploadFile(): {} ", UPLOAD_FAILED + e.getMessage());
            throw new RuntimeException("uploadFile() : {}" + e.getMessage());
        }
    }
}
