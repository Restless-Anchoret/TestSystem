/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.conservator;

import com.netcracker.monitoring.info.Results;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.logging.MonitoringLogging;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Магистраж
 */
public class XmlResultsConservator implements ResultsConservator {

    public static final Logger logger = MonitoringLogging.logger;

    @Override
    public List<TotalResultInfo> getVisibleResults(String competitionFolder) {
        try {

            File file = new File(competitionFolder + "//" + "visible_results.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Results results = (Results) jaxbUnmarshaller.unmarshal(file);
            return results.getTotalResult();
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "some errors occured during unmarshalling: {0}", e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean persistVisibleResults(String competitionFolder, List<TotalResultInfo> results) {
        try {
            Results t = new Results();
            t.setTotalResult(results);
            File file = new File("conserved_files//" + competitionFolder + "//" + "visible_results.xml");
            file.getParentFile().mkdirs();
            JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(t, file);
            return true;
        } catch (JAXBException jAXBException) {
            logger.log(Level.SEVERE, "some errors occured during marshalling: {0}", jAXBException.toString());
        }
        return false;
    }

}
