package com.netcracker.web.Filters;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.businesslogic.users.Role;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ParticipationFacadeLocal;
import com.netcracker.database.dal.UserFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.User;
import com.netcracker.monitoring.info.CompetitionPhase;
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


@WebFilter(filterName = "CompetitionFilter", urlPatterns = {"/faces/competition/*"})
public class CompetitionFilter implements Filter{

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "ParticipationFacade")
    private ParticipationFacadeLocal participationFacade;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("role");
        String userId = (String) session.getAttribute("userId");
        String competitionId = req.getParameter("competitionId");
        Integer id;
        if (userRole == null || userId == null) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        if (userRole.toLowerCase().equals(Role.ADMIN.toString().toLowerCase())
                || userRole.toLowerCase().equals(Role.MODERATOR.toString().toLowerCase())) {
            chain.doFilter(request, response);
            return;
        }
        try {
            id = Integer.parseInt(competitionId);
        }
        catch (NumberFormatException ex) {
            chain.doFilter(request, response);
            return;
        }
        Competition competition = competitionFacade.find(id);
        try {
            id = Integer.parseInt(userId);
        }
        catch (NumberFormatException ex) {
            res.sendRedirect("/WebModule/faces/guest/log_in.xhtml");
            return;
        }
        if (competition == null) {
            chain.doFilter(request, response);
            return;
        }
        Path path = Paths.get(req.getRequestURI());
        String pageName = path.getFileName().toString();
        boolean resultCheck;
        switch (pageName) {
            case "competition_registration.xhtml":
                resultCheck = registrationCheck(competition);
                break;
            case "competition_monitor.xhtml":
                resultCheck = competitionCheck(competition.getId(), id) && 
                        competition.getShowMonitor();
                break;
            default:
                resultCheck = competitionCheck(competition.getId(), id);
                break;
        }
        if (resultCheck)
            chain.doFilter(request, response);
        else
            res.sendRedirect("/WebModule/faces/errorPage/accessError.xhtml");
    }

    private boolean registrationCheck(Competition competition) {
        switch (competitionEJB.getRegistrationType(competition)) {
            case MODERATION:
            case PUBLIC:
                return competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.BEFORE;
            case CLOSED:
                return false;
        }
        return false;
    }
    
    private boolean competitionCheck(Integer competitionId, Integer userId) {
        Participation participation = participationFacade.findByCompetitionIdAndUserId(
                competitionId, userId);
        if (participation == null)
            return false;
        return participation.getRegistered();
    }
    
    @Override
    public void destroy() {

    }
}
