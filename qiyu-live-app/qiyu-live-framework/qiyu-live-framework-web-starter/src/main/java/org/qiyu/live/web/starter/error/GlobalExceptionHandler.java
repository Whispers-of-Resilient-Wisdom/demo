package org.qiyu.live.web.starter.error;

import com.qiyu.live.common.interfaces.VO.WebResponseVO;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器 GlobalExceptionHandler
 * <p>
 * 该类使用 @ControllerAdvice 注解，统一处理所有 Controller 层抛出的异常，
 * 提供对系统异常和自定义业务异常的统一响应机制，避免在每个 Controller 中单独编写异常处理逻辑。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 通用异常处理方法
     * <p>
     * 捕获所有未被其他 @ExceptionHandler 处理的异常（Exception.class），
     * 打印异常信息，并返回统一的系统错误响应。
     *
     * @param request HttpServletRequest 请求对象，可用于记录请求路径等信息
     * @param e       Exception 捕获的异常对象
     * @return WebResponseVO 系统异常的统一响应结构，包含错误信息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public WebResponseVO errorHandler(HttpServletRequest request, Exception e) {
        // 记录系统异常信息，便于排查问题
        LOGGER.error("{}, error is ", request.getRequestURI(), e);
        // 返回系统异常响应信息
        return WebResponseVO.sysError("系统异常");
    }

    /**
     * 自定义业务异常处理方法
     * <p>
     * 专门处理 QiyuErrorException 类型的自定义业务异常，
     * 打印异常信息，并返回业务错误的统一响应。
     *
     * @param request HttpServletRequest 请求对象，可用于记录请求路径等信息
     * @param e       QiyuErrorException 捕获的自定义异常对象
     * @return WebResponseVO 业务异常的统一响应结构，包含错误码和错误信息
     */
    @ExceptionHandler(value = QiyuErrorException.class)
    @ResponseBody
    public WebResponseVO sysErrorHandler(HttpServletRequest request, QiyuErrorException e) {
        // 记录业务异常信息，便于分析具体的业务错误
        LOGGER.error("{}, error code is {}, error msg is {}", request.getRequestURI(), e.getErrorCode(), e.getErrorMsg());
        // 返回业务异常响应信息
        return WebResponseVO.bizError(e.getErrorCode(), e.getErrorMsg());
    }
}

