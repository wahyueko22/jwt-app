package com.token.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.token.object.BaseResponseDTO;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    private ResponseEntity<Object> buildResponseEntity(BaseResponseDTO baseDTO) {
        return new ResponseEntity<>(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<Object> customException(CustomException e) {
        logger.error("BusinessException ", e);
        return new ResponseEntity<> (new BaseResponseDTO(Constant.RESPONSE_STRING_ERROR, "417", e.getCode(),e.getErrorMessage(),null), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(Exception.class) 
    private ResponseEntity<Object> errException(Exception e) {
        logger.error(e.getMessage(), e);
        BaseResponseDTO baseDto = new BaseResponseDTO();
        baseDto.setStatus(Constant.RESPONSE_STRING_ERROR);
        baseDto.setCode("500");
        baseDto.setErrCode(Constant.Error.ERROR_DATA_500.getCode());
        baseDto.setMessage(Constant.Error.ERROR_DATA_500.getErrorMessage());
        return this.buildResponseEntity(baseDto);
    }

}