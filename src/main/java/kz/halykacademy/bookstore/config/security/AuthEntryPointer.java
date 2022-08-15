package kz.halykacademy.bookstore.config.security;

import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointer {
//        extends BasicAuthenticationEntryPoint {

//    @Override
//    public void commence(
//            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
//            throws IOException {
//        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        PrintWriter writer = response.getWriter();
//        writer.println("HTTP Status " + HttpServletResponse.SC_FORBIDDEN + " - " + authEx.getMessage());
//    }
//
//    @Override
//    public void afterPropertiesSet() {
//        setRealmName("BookStore");
//        super.afterPropertiesSet();
//    }
}
