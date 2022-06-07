package com.tsunglin.tsunglin00.config.handler;

import com.tsunglin.tsunglin00.config.annotation.TokenToAdminUser;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.dao.AdminUserTokenMapper;
import com.tsunglin.tsunglin00.entity.AdminUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AdminUserTokenMapper adminUserTokenMapper;

    public TokenToAdminUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH) {
                AdminUserToken adminUserToken = adminUserTokenMapper.selectByToken(token);
                if (adminUserToken == null) {
                    Exception.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
                } else if (adminUserToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
                    Exception.fail(ServiceResultEnum.ADMIN_TOKEN_EXPIRE_ERROR.getResult());
                }
                return adminUserToken;
            } else {
                Exception.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
            }
        }
        return null;
    }

}
