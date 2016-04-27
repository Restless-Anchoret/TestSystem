package com.netcracker.web.util;

import com.netcracker.monitoring.info.CompetitionPhase;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("competitionPhaseConverter")
public class CompetitionPhaseConverter implements Converter {
    
    private static final String BEFORE_DESCRIPTION = "Ожидается";
    private static final String CODING_DESCRIPTION = "Идёт";
    private static final String CODING_FROZEN_DESCRIPTION = "Идёт, заморозка";
    private static final String WAITING_RESULTS_DESCRIPTION = "Ожидаются результаты";
    private static final String FINISHED_DESCRIPTION = "Завершено";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case BEFORE_DESCRIPTION: return CompetitionPhase.BEFORE;
            case CODING_DESCRIPTION: return CompetitionPhase.CODING;
            case CODING_FROZEN_DESCRIPTION: return CompetitionPhase.CODING_FROZEN;
            case WAITING_RESULTS_DESCRIPTION: return CompetitionPhase.WAITING_RESULTS;
            case FINISHED_DESCRIPTION: return CompetitionPhase.FINISHED;
            default: return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof CompetitionPhase)) {
            return null;
        }
        CompetitionPhase phase = (CompetitionPhase)value;
        switch (phase) {
            case BEFORE: return BEFORE_DESCRIPTION;
            case CODING: return CODING_DESCRIPTION;
            case CODING_FROZEN: return CODING_FROZEN_DESCRIPTION;
            case WAITING_RESULTS: return WAITING_RESULTS_DESCRIPTION;
            case FINISHED: return FINISHED_DESCRIPTION;
            default: return null;
        }
    }

}