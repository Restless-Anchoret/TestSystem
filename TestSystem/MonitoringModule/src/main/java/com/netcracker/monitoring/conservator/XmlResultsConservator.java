package com.netcracker.monitoring.conservator;

import com.netcracker.monitoring.delegate.FileSystemDelegate;
import com.netcracker.monitoring.info.Results;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.logging.MonitoringLogging;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlResultsConservator implements ResultsConservator {

    private FileSystemDelegate fileSystemDelegate;

    public XmlResultsConservator(FileSystemDelegate fileSystemDelegate) {
        this.fileSystemDelegate = fileSystemDelegate;
    }

    @Override
    public List<TotalResultInfo> getVisibleResults(String competitionFolder) {
        try {
            Path path = fileSystemDelegate.getCompetitionVisibleResults(competitionFolder, true);
            if (path == null) {
                return null;
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Results results = (Results) jaxbUnmarshaller.unmarshal(path.toFile());
            return results.getTotalResult();
        } catch (JAXBException exception) {
            MonitoringLogging.logger.log(Level.FINE, "JAXBException while unmarshalling", exception);
            return null;
        }
    }

    @Override
    public boolean persistVisibleResults(String competitionFolder, List<TotalResultInfo> results) {
        try {
            Results resultsWrap = new Results(results);
            File file = (fileSystemDelegate.getCompetitionVisibleResults(competitionFolder, false).toFile());
            JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(resultsWrap, file);
            return true;
        } catch (JAXBException exception) {
            MonitoringLogging.logger.log(Level.FINE, "JAXBException while marshalling", exception);
            return false;
        }
    }

}
