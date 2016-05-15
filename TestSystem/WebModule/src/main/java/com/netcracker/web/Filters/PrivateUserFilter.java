package com.netcracker.web.Filters;

import com.netcracker.businesslogic.users.Role;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(filterName = "PrivateUserFilter", urlPatterns = {"/faces/privateUser/*"})
public class PrivateUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("role");
        if (userRole == null) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        if (userRole.toLowerCase().equals(Role.ADMIN.toString().toLowerCase()) ||
            userRole.toLowerCase().equals(Role.MODERATOR.toString().toLowerCase())) {
            chain.doFilter(request, response);
            return;
        }
        String param;
        int userSessionId, userParametrId;
        param = (String) session.getAttribute("userId");
        try {
            userSessionId = Integer.parseInt(param);
        } catch (NumberFormatException ex) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        param = req.getParameter("userId");
        try {
            userParametrId = Integer.parseInt(param);
        } catch (NumberFormatException ex) {
            chain.doFilter(request, response);
            return;
        }
        if (userParametrId == userSessionId)
            chain.doFilter(request, response);
        else
            res.sendRedirect("/WebModule/faces/errorPage/accessError.xhtml");
    }

    @Override
    public void destroy() {}

}
