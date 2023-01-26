package com.clusus.Bloomberg.controller;

import com.clusus.Bloomberg.service.ForexDealService;
import com.clusus.Bloomberg.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.clusus.Bloomberg.util.APIConstant.API_PATH;
import static com.clusus.Bloomberg.util.APIConstant.UPLOAD;

@RestController
@RequestMapping(API_PATH)
public class ForexDealController {

    private final Logger LOGGER = LoggerFactory.getLogger(ForexDealController.class);

    private final ForexDealService forexDealService;

    public ForexDealController(ForexDealService forexDealService) {
        this.forexDealService = forexDealService;
    }

    @PostMapping(UPLOAD)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam MultipartFile file) {
        LOGGER.debug("uploadFile(): ForexDealController : filename: {}", file.getOriginalFilename());
        String message = forexDealService.uploadFile(file);
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }
}
