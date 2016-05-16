package com.netcracker.web.Filters;

import com.netcracker.businesslogic.users.Role;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.ejb.EJB;
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

    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    
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
        int userSessionId, userParametrId = 0;
        param = (String) session.getAttribute("userId");
        try {
            userSessionId = Integer.parseInt(param);
        } catch (NumberFormatException ex) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        param = req.getParameter("userId");
        Path path = Paths.get(req.getRequestURI());
        String pageName = path.getFileName().toString();
        try {
            userParametrId = Integer.parseInt(param);
        } catch (NumberFormatException ex) {
            if (!pageName.equals("submission_code.xhtml")) {
                chain.doFilter(request, response);
                return;
            }
        }
        boolean result;
        if (pageName.equals("submission_code.xhtml"))
            result = submissionPageCheck(userSessionId, req);
        else
            result = (userParametrId == userSessionId);
        if (result)
            chain.doFilter(request, response);
        else
            res.sendRedirect("/WebModule/faces/errorPage/accessError.xhtml");
    }

    private boolean submissionPageCheck(int userId, HttpServletRequest request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException ex) {
            return true;
        }
        Submission submission = submissionFacade.find(id);
        if (submission == null)
            return true;
        else
            return submission.getUserId().getId() == userId;
    }
    
    @Override
    public void destroy() {}

}
