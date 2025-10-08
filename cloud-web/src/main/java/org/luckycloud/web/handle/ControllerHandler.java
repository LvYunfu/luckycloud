package org.luckycloud.web.handle;

import lombok.extern.log4j.Log4j2;
import org.luckycloud.dto.common.Response;
import org.luckycloud.exception.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;

import static org.luckycloud.dto.common.ResponseCode.SYS_ERROR;

/**
 * @author lvyf
 * @description:
 * @date 2023/4/9 15:47
 */
@Log4j2

@RestControllerAdvice
public class ControllerHandler implements ResponseBodyAdvice<Object> {

    @ExceptionHandler({BusinessException.class})
    public Response<Void> handlerManageException(BusinessException e) {
        log.error("业务异常", e);
        return Response.exception(e);
    }



//    @ExceptionHandler({ValidationException.class})
//    public ApiResponse handlerValidException(ValidationException e) {
//        log.error("参数检验失败", e);
//        return new ApiResponse(ResponseCode.VALIDATE_FAILED);
//    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response<Void>  handlerArgueException(MethodArgumentNotValidException e) {
        log.error("参数异常", e);
        return Response.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({Exception.class})
    public Response<Void> handlerException(Exception e) {
        log.error("系统异常", e);
        return new Response<>(SYS_ERROR);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().isAssignableFrom(Response.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Type resType = returnType.getGenericParameterType();
//        if (resType.equals(String.class)) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                return objectMapper.writeValueAsString(Response.success(body));
//            } catch (JsonProcessingException e) {
//                throw new BusinessException(ResponseCode.OPERATE_FAILED,"");
//            }
//        }
//        if (resType.equals(void.class)) {
//            return null;
//        }
        return Response.successData(body);
    }
}
