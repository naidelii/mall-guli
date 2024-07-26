package com.mall.generator.handler;

import cn.hutool.json.JSONUtil;
import com.mall.common.base.api.Result;
import com.mall.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author naidelii
 */
@Component
@Slf4j
public class GeneratorExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        String errorMsg;
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            if (ex instanceof GeneratorException) {
                errorMsg = ex.getMessage();
            } else if (ex instanceof DuplicateKeyException) {
                errorMsg = "数据库中已存在该记录";
            } else {
                errorMsg = "未知异常";
            }
            //记录异常日志
            log.error(ex.getMessage(), ex);
            String json = JSONUtil.toJsonStr(Result.fail(errorMsg));
            response.getWriter().print(json);
        } catch (Exception e) {
            log.error("RRExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }
}
