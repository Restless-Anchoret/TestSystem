package com.netcracker.web.Filters;

import com.netcracker.businesslogic.users.Role;
import com.netcracker.database.dal.ParticipationFacadeLocal;
import com.netcracker.database.entity.Participation;
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


@WebFilter(filterName = "ParticipantFilter", urlPatterns = {"/faces/participant/*"})
public class ParticipantFilter implements Filter {

    @EJB(beanName = "ParticipationFacade")
    private ParticipationFacadeLocal participationFacade;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String userRole = (String)session.getAttribute("role");
        if (userRole == null) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        if (userRole.toLowerCase().equals(Role.ADMIN.toString().toLowerCase()) ||
            userRole.toLowerCase().equals(Role.MODERATOR.toString().toLowerCase())) {
            chain.doFilter(request, response);
            return;
        }
        Path path = Paths.get(req.getRequestURI());
        String pageName = path.getFileName().toString();
        String param;
        Integer userId;
        param = (String) session.getAttribute("userId");
        try {
            userId = Integer.parseInt(param);
        } catch (NumberFormatException ex) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        if (pageName.equals("downloadFile.xhtml")) {
            Integer competitionId;
            param = req.getParameter("competitionId");
            try {
                competitionId = Integer.parseInt(param);
            } catch (NumberFormatException ex) {
                res.sendRedirect("/WebModule/faces/errorPage/accessError.xhtml");
                return;
            }
            if (downloadFileCheck(competitionId, userId)) 
                chain.doFilter(request, response);
            else
                res.sendRedirect("/WebModule/faces/errorPage/accessError.xhtml");
        }
        else {
            chain.doFilter(request, response);
        }
    }

    private boolean downloadFileCheck(Integer competitionId, Integer userId) {
        Participation participation = participationFacade.findByCompetitionIdAndUserId(
                competitionId, userId);
        if (participation == null) {
            return false;
        }
        return participation.getRegistered();
    }
    
    @Override
    public void destroy() {}
    
}
