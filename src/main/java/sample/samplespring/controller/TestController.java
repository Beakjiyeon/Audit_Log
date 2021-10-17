package sample.samplespring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.samplespring.dto.ApiResult;
import sample.samplespring.exception.*;

@RestController
@Slf4j
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/server/hello")
    public String connectTest(){
        return "Hello, I am Auth Server.";
    }


    @ExceptionHandler(SampleException.class)
    @GetMapping("/server/exception/{e}")
    public ResponseEntity exceptionTest(@PathVariable(value = "e") String e){
        if (e.equals("404exceptiontest")) {
            log.info("exceptionTest function >> throw custom exception .");
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return new ApiResult(200, "success", null).getResponse();
    }
}
