package com.clusus.Bloomberg.exception_handler;

import com.clusus.Bloomberg.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

import static com.clusus.Bloomberg.util.MessageConstant.FILE_SIZE_TOO_LARGE;
import static com.clusus.Bloomberg.util.MessageConstant.INVALID_FILE_FORMAT;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        LOGGER.warn(FILE_SIZE_TOO_LARGE);
        return new ResponseEntity<>(new ResponseMessage(FILE_SIZE_TOO_LARGE), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(InvalidFileFormat.class)
    public ResponseEntity<ExceptionResponseDTO> emptyListException(final InvalidFileFormat ex, final HttpServletRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(ex.getLocalizedMessage(), request.getRequestURI());
        LOGGER.warn(INVALID_FILE_FORMAT);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

