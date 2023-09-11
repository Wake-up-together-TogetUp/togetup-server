package com.wakeUpTogetUp.togetUp.api.auth;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasAnnotation = parameter.hasParameterAnnotation(AuthUser.class);
        boolean isInteger = parameter.getParameterType().equals(Integer.class);
        return hasAnnotation && isInteger;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        if(webRequest.getHeader("Authorization")==null)
          throw  new BaseException(Status.UNAUTHORIZED);
        String jwt = webRequest.getHeader("Authorization").replace("Bearer", "").trim();
        if(jwt.isEmpty())
            throw new BaseException(Status.UNAUTHORIZED);

        return jwtService.getUserId(jwt);
    }

}

