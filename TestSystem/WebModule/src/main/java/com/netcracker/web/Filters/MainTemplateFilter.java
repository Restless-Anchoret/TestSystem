package com.netcracker.web.Filters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

@WebFilter(filterName = "MainTemplateFilter", urlPatterns = {"/faces/*"})
public class MainTemplateFilter implements Filter {

    public static final String MAIN_MENU_ACTIVE_ITEM = "main_menu_active_item";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Path path = Paths.get(req.getRequestURI());
        String pageName = path.getFileName().toString();
        if (pageName.startsWith("competition")) {
            session.setAttribute(MAIN_MENU_ACTIVE_ITEM, 0);
        } else if (pageName.startsWith("user")) {
            session.setAttribute(MAIN_MENU_ACTIVE_ITEM, 1);
        } else if (pageName.startsWith("moderation")) {
            session.setAttribute(MAIN_MENU_ACTIVE_ITEM, 2);
        } else {
            session.setAttribute(MAIN_MENU_ACTIVE_ITEM, -1);
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() { }
    
}