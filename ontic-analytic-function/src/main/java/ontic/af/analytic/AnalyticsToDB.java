package ontic.af.analytic;


import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import ontic.af.action.db.entity.AffectedKPI;
import ontic.af.action.db.entity.AffectedKPI_PK;
import ontic.af.action.db.entity.AffectedLocation;
import ontic.af.action.db.entity.AffectedLocation_PK;
import ontic.af.action.db.entity.AffectedSegmentation;
import ontic.af.action.db.entity.AffectedSegmentation_PK;
import ontic.af.action.db.entity.DegradationReport;
import ontic.af.action.db.repository.AffectedKPIRepository;
import ontic.af.action.db.repository.AffectedLocationRepository;
import ontic.af.action.db.repository.AffectedSegmentationRepository;
import ontic.af.action.db.repository.DegradationReportRepository;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement

public class AnalyticsToDB implements AnalyticFunctionIF {
	
	@Autowired
	DegradationReportRepository degradationReportRepo;
	
	@Autowired
	AffectedKPIRepository affectedKPIRepo;
	
	@Autowired
	AffectedLocationRepository affectedLocationRepo;
	
	@Autowired
	AffectedSegmentationRepository affectedSegmentationRepo;

	public void analyze(Message<byte[]> message) {
		
		byte[] analytic_input= message.getPayload();
		
		System.out.println("--->"+new String (analytic_input));
		// Analyze
		//
		Timestamp creationTime=new Timestamp(System.currentTimeMillis());
		Timestamp endTime=new Timestamp(System.currentTimeMillis()+30000000);
		
		saveReport("123456",creationTime,endTime,80.1,"video_accessibility","132901849", "Gold");
		
	}
	
	private void saveReport(String report_id, Timestamp creationTime, Timestamp endTime, Double confidence,  String affectedKPI_Id, String affectedLocationId, String affectedSegmentationId)
	{
		DegradationReport report = new DegradationReport();
		
		report.setId(report_id);
		report.setCreationDateTime(creationTime);
		report.setEndDateTime(endTime);
		report.setConfidence(confidence);
		
		
		AffectedKPI_PK affectedKPI_PK = new AffectedKPI_PK();
		affectedKPI_PK.setKpi_id(affectedKPI_Id);
		affectedKPI_PK.setDegradation_report_id(report_id);
		AffectedKPI affectedPKI= new AffectedKPI();
		affectedPKI.setAffectedKPI_PK(affectedKPI_PK);
		
		AffectedLocation_PK affectedLocation_PK = new AffectedLocation_PK();
		affectedLocation_PK.setLocation_id(affectedLocationId);
		affectedLocation_PK.setDegradation_report_id(report_id);
		AffectedLocation affectedLocation= new AffectedLocation();
		affectedLocation.setAffectedLocation_PK(affectedLocation_PK);
		
		
		AffectedSegmentation_PK affectedSegmentation_PK = new AffectedSegmentation_PK();
		affectedSegmentation_PK.setSegmentation_id( affectedSegmentationId);
		affectedSegmentation_PK.setDegradation_report_id(report_id);
		AffectedSegmentation affectedSegmentation= new AffectedSegmentation();
		affectedSegmentation.setAffectedSegmentation_PK(affectedSegmentation_PK);
		saveDegradationReport(report);
		
		saveAffected (affectedPKI, affectedLocation, affectedSegmentation);
		

	}
	
	@Transactional
	
	public void saveDegradationReport(DegradationReport report)
	{
		degradationReportRepo.save(report);
		
	}
	
	@Transactional
	
	public void saveAffected(AffectedKPI affectedKPI,AffectedLocation affectedLocation, AffectedSegmentation affectedSegmentation)
	{ 
					
		affectedKPIRepo.save(affectedKPI);
		affectedLocationRepo.save(affectedLocation);
		affectedSegmentationRepo.save(affectedSegmentation);
		
		
	}
	
	
	
	

}
