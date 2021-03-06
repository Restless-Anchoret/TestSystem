package com.netcracker.web.util;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.web.logging.WebLogging;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {
    
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(competitionEJB.getTimeZoneId()));
        Date result;
        try {
            result = format.parse(value);
        } catch (ParseException ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            return null;
        }
        return result;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Date date = (Date) value;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(competitionEJB.getTimeZoneId()));
        return format.format(date);
    }

}