package com.jdbctemplate.employee.config;

import com.jdbctemplate.employee.entity.User;
import com.jdbctemplate.employee.model.ManagerModel;
import com.jdbctemplate.employee.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

@Component
public class JwtInterceptor extends WebRequestHandlerInterceptorAdapter {

    @Autowired
    JwtUtils jwtUtils;

    public JwtInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("authorization");

        if( !(request.getRequestURI().contains("login") || request.getRequestURI().contains("signup"))){
//            Claims claims = jwtUtils.verify(auth);

            jwtUtils.verify(auth);
//
//          User.setUserName(claims.get("name").toString());
//            User.setUserId(Math.toIntExact(Long.valueOf(claims.getIssuer())));
//            User.setUserType(claims.get("type").toString());
//            User.setEmailId(claims.get("emailId").toString());

        }

        return super.preHandle(request, response, handler);
    }
}
