package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.holding.RegistrationType;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.testing.evaluation.EvaluationSystemRegistry;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class ModeratingCompetitionEJB {
    
    private static final String DEFAULT_COMPETITION_NAME = "New competition";
    private static final Integer DEFAULT_COMPETITION_INTERVAL = 120;
    private static final Integer DEFAULT_INTERVAL_FROZEN = 0;
    
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    
    public List<Competition> getAllCompetitions() {
        try {
            return competitionFacade.findAllCompetiotions(new int[] {0, 30});
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while getting all competitions", exception);
            return Collections.EMPTY_LIST;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Competition addNewCompetition() {
        try {
            Competition competition = new Competition();
            competition.setName(DEFAULT_COMPETITION_NAME);
            String evaluationType = EvaluationSystemRegistry.registry().getAvailableIds().iterator().next();
            competition.setEvaluationType(evaluationType);
            competition.setRegistrationType(RegistrationType.PUBLIC.toString().toLowerCase());
            competition.setHoldCompetition(true);
            competition.setCompetitionStart(getFutureDate());
            competition.setCompetitionInterval(DEFAULT_COMPETITION_INTERVAL);
            competition.setIntervalFrozen(DEFAULT_INTERVAL_FROZEN);
            competitionFacade.create(competition);
            String competitionFolder = competition.getId().toString();
            applicationEJB.getFileSupplier().addCompetitionFolder(competitionFolder);
            competition.setFolderName(competitionFolder);
            competitionFacade.edit(competition);
            return competition;
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while trying to add new competition", exception);
            return null;
        }
    }
    
    private Date getFutureDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }
    
}