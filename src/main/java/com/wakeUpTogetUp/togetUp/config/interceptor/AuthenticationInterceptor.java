package com.wakeUpTogetUp.togetUp.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.config.annotation.NoAuth;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper; //자바 객체를 json으로 serialization

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean check=checkAnnotation(handler, NoAuth.class);

        if(check) return true;

        try{
            int userNumByJwt = jwtService.getUserNum();

            request.setAttribute("userId",userNumByJwt);
        }catch(BaseException exception){
            String requestURI= request.getRequestURI();

            Map<String,String> map=new HashMap<>();
            map.put("requestURI","/app/accounts/auth?redirectURI="+requestURI);
            //redirectURI는 로그인 절차가 끝내고 다시 시도했던 페이지로 돌아가기 위해 JSON 정보에 포함시킨다.
            String json=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            response.getWriter().write(json);

            return false;
        }
        return true;
    }

    private boolean checkAnnotation(Object handler,Class cls){
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(cls)!=null){ //해당 어노테이션이 존재하면 true.
            return true;
        }
        return false;
    }
}
