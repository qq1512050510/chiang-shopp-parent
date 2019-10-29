package com.chiang.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chiang.constant.Constants;
import com.chiang.exception.AuthcTypeNotSupoortException;
import com.chiang.exception.UnauthenticatedException;

/**
 * 
 * @author Administrator
 *
 */
@ControllerAdvice
/*@RestControllerAdvice*/
public class WebExceptonHandler {
	private static final Logger log = LoggerFactory.getLogger(WebExceptonHandler.class);

	/**
	 * 捕获未授权异常 跳转登陆页面
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	public String unauthenticatedException(UnauthenticatedException exception) {
		// return "redirect:" + shiroFilterFactoryBean.getLoginUrl();
		log.info("ControllerAdvice捕获异常{}", exception.getMessage());
		return "redirect:/errorException";
	}

	/**
	 * 授权类型不支持
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	public String authcTypeNotSupportException(AuthcTypeNotSupoortException exception) {

		if (log.isDebugEnabled()) {
			log.debug(exception.getMessage(), exception);
		}
		return generateErrorInfo(Constants.FAIL, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	/**
	 * 其他所有异常处理接口
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	public String all(Exception exception) {
		String msg = StringUtils.isEmpty(exception.getMessage()) ? "系统出现异常" : exception.getMessage();
		log.error(msg, exception);
		generateErrorInfo(Constants.FAIL, msg);
		//return "forward:/error";
		return "forward:/errorException";
	}

	/**
	 * 生成错误信息，放到 request 域中
	 * 
	 * @param code
	 * @param msg
	 * @param httpStatus
	 * @return SpringBoot 默认提供的 /error Controller处理器
	 */
	private String generateErrorInfo(int code, String msg, int httpStatus) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.setAttribute("code", code);
		request.setAttribute("msg", msg);
		request.setAttribute("javax.servlet.error.status_code", httpStatus);
		return "forward:/error";
	}

	/**
	 * 生成错误信息，放到 request 域中
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	private String generateErrorInfo(int code, String msg) {
		return generateErrorInfo(code, msg, HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	/**
	 * 捕获 ClientAbortException 异常，不做任务处理，放置出现大量堆栈日志出现，此异常不影响功能
	 * 
	 * @param exception
	 */
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class, ClientAbortException.class })
	public void clientAbortException(Exception exception) {
		if (log.isDebugEnabled()) {
			log.debug("出现了断开异常：{}", exception);
		}
	}

}
