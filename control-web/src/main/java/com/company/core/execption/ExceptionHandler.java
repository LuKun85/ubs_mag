package com.company.core.execption;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.company.core.util.BasisException;
import com.company.core.util.BjuiAjaxReturnStatusUtil;

/**
 * 总异常处理类 处理所有spring管理的运行时异常
 * @author Administrator
 */

@Component
public class ExceptionHandler implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        logger.error("出错了", ex);
        String error = "";
        if (ex instanceof ArithmeticException) {
            request.setAttribute("exStackTrace", getExceptionStackTraceMessage(ex));
            return new ModelAndView("error");
        } else if (ex instanceof UnauthenticatedException || ex instanceof UnauthorizedException) {
            return null;
        } else if (ex instanceof DuplicateKeyException) {
            error = BjuiAjaxReturnStatusUtil.error("数据已存在");
        } else if (ex instanceof BasisException) {
            error = BjuiAjaxReturnStatusUtil.error(ex.getMessage());
        } else {
            error = BjuiAjaxReturnStatusUtil.error("未知异常");
        }
        getJSONFromList(response, error);
        return null;
    }

    private String getExceptionStackTraceMessage(Exception ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        ex.printStackTrace(printWriter);
        return result.toString();
    }

    private void getJSONFromList(HttpServletResponse response, String jo) {
        response.setContentType("text/plain" + ";charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(jo);
            response.getWriter().flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}