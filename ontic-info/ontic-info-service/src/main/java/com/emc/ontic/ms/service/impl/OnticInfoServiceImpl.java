package com.emc.ontic.ms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emc.ontic.ms.commons.domain.DeliveryService;
import com.emc.ontic.ms.commons.domain.ModifyDeliveryConditionsRequest;
import com.emc.ontic.ms.commons.domain.ModifySubscriptionRequest;
import com.emc.ontic.ms.commons.domain.ReportRequest;
import com.emc.ontic.ms.commons.domain.StartSubscriptionRequest;
import com.emc.ontic.ms.commons.domain.StopDeliveryConditionsRequest;
import com.emc.ontic.ms.commons.domain.StopSubscriptionRequest;
import com.emc.ontic.ms.commons.domain.ReportRequest.ReportRequestKpiServiceValue;
import com.emc.ontic.ms.data.entity.Kpi;
import com.emc.ontic.ms.data.entity.Location;
import com.emc.ontic.ms.data.entity.Session;
import com.emc.ontic.ms.data.entity.SessionKpi;
import com.emc.ontic.ms.data.entity.SessionKpiPK;
import com.emc.ontic.ms.data.entity.SessionLocation;
import com.emc.ontic.ms.data.entity.SessionLocationPK;
import com.emc.ontic.ms.data.entity.Subscriber;
import com.emc.ontic.ms.data.entity.Subscription;
import com.emc.ontic.ms.data.entity.SubscriptionKpi;
import com.emc.ontic.ms.data.entity.SubscriptionKpiPK;
import com.emc.ontic.ms.data.entity.SubscriptionLocation;
import com.emc.ontic.ms.data.entity.SubscriptionLocationPK;
import com.emc.ontic.ms.data.repository.KpiRepository;
import com.emc.ontic.ms.data.repository.LocationRepository;
import com.emc.ontic.ms.data.repository.SessionKpiRepository;
import com.emc.ontic.ms.data.repository.SessionLocationRepository;
import com.emc.ontic.ms.data.repository.SessionRepository;
import com.emc.ontic.ms.data.repository.SubscriberRepository;
import com.emc.ontic.ms.data.repository.SubscriptionKpiRepository;
import com.emc.ontic.ms.data.repository.SubscriptionLocationRepository;
import com.emc.ontic.ms.data.repository.SubscriptionRepository;
import com.emc.ontic.ms.service.OnticInfoService;

@Service
public class OnticInfoServiceImpl implements OnticInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(OnticInfoServiceImpl.class);
	
	@Autowired
	SubscriberRepository subscriberRepo;
	
	@Autowired
	SubscriptionRepository subscriptionRepo;
	
	@Autowired
	KpiRepository kpiRepo;
	
	@Autowired
	LocationRepository locationRepo;
	
	@Autowired
	SubscriptionKpiRepository subscriptionKpiRepo;
	
	@Autowired
	SubscriptionLocationRepository subscriptionLocRepo;
	
	@Autowired
	SessionRepository sessionRepo;
	
	@Autowired
	SessionKpiRepository sessionKpiRepo;
	
	@Autowired
	SessionLocationRepository sessionLocRepo;
	
	public OnticInfoServiceImpl() {
		super();
		logger.info("OnticInfoServiceImpl.OnticInfoServiceImpl()");
		
	}
	
	@Transactional
	public void startSubscription(StartSubscriptionRequest request) throws Exception {
		logger.info("OnticInfoServiceImpl::StartSubscriptionRequest Start");
		
		Subscription subscription = new Subscription();
		
		if(!StringUtils.isEmpty(request.getResourceURI())) {
			Subscriber resultSubscriber = subscriberRepo.findByUrl(request.getResourceURI());
			if(resultSubscriber == null) {
				logger.info("Subscriber does not exist : {}", request.getResourceURI());
				Subscriber newSubscriber = new  Subscriber();
				newSubscriber.setUrl(request.getResourceURI());
				Subscriber savedSubscriber = subscriberRepo.save(newSubscriber);
				logger.info("Saved the subscriber : {}", request.getResourceURI());
				subscription.setSubscriberId(savedSubscriber.getId());
			} else {
				logger.info("Subscriber already exists : {}", request.getResourceURI());
				subscription.setSubscriberId(resultSubscriber.getId());
			}
		}
		
		subscription.setId(request.getSubsID());
		subscription.setFrequency(request.getFrequency());
		
		Subscription resultSubscription = subscriptionRepo.save(subscription);
		logger.info("Saved subscription : {}", resultSubscription.getId());
		
		//Save SubscriptionKPIs
		saveSubscriptionKpis(request.getKpi(), request.getSubsID());
		
		//Save SubscriptionLocations
		saveSubscriptionLocations(request.getLocation(), request.getSubsID());
		
		logger.info("OnticInfoServiceImpl::StartSubscriptionRequest End");
	}
	
	@Transactional
	public void modifySubscription(ModifySubscriptionRequest request) {
		logger.info("OnticInfoServiceImpl::modifySubscription Start");
		String subsID = request.getSubsID();
		
		Subscription subscription = subscriptionRepo.findById(subsID);
		
		if(subscription == null) {
			throw new RuntimeException("No subscription found with subscription id : "+ subsID);
		}
		
		if(request.getFrequency() != null && request.getFrequency() > 0) {
			subscription.setFrequency(request.getFrequency());
		}
		
		if(StringUtils.isNotBlank(request.getResourceURI())) {
			Subscriber subscriber = subscriberRepo.findById(subscription.getSubscriberId());
			subscriber.setUrl(request.getResourceURI());
			subscriberRepo.save(subscriber);
		}
		
		subscriptionRepo.save(subscription);
		logger.info("Updated subscription : {}", subsID);
		
		if(request.getLocation() != null && request.getLocation().length > 0) {
			List<SubscriptionLocation> locList = subscriptionLocRepo.findBySubscriptionId(subsID);
			//subscriptionLocRepo.deleteInBatch(locList);
			subscriptionLocRepo.delete(locList);
			
			saveSubscriptionLocations(request.getLocation(), subsID);
			logger.info("Updated Locations for subscription : {}", subsID);
		}
		
		List<SubscriptionKpi> kpiList = subscriptionKpiRepo.findBySubscriptionId(subsID);
		
		if(StringUtils.isNotBlank(request.getService()) && (request.getKpi() == null || request.getKpi().length == 0)) { 
			
			//subscriptionKpiRepo.deleteInBatch(kpiList);
			subscriptionKpiRepo.delete(kpiList);
			
			subscriptionKpiRepo.flush();
			
			List<Kpi> kpis = kpiRepo.findByServiceId(request.getService());
			
			if(kpis != null && !kpis.isEmpty()) {
				String[] kpiArr = new String[kpis.size()];
				for(int i=0 ; i<kpis.size() ; i++) {
					Kpi kpi = kpis.get(i);
					kpiArr[i] = kpi.getId();
				}
				
				saveSubscriptionKpis(kpiArr, subsID);
				logger.info("Updated KPIs for subscription : {}", subsID);
			}
		} else if(StringUtils.isBlank(request.getService()) && (request.getKpi() != null && request.getKpi().length > 0)) { 
			//subscriptionKpiRepo.deleteInBatch(kpiList);
			subscriptionKpiRepo.delete(kpiList);
			
			subscriptionKpiRepo.flush();
			
			saveSubscriptionKpis(request.getKpi(), subsID);
			logger.info("Updated KPIs for subscription : {}", subsID);
		} else if(StringUtils.isNotBlank(request.getService()) && (request.getKpi() != null && request.getKpi().length > 0)) {
			//subscriptionKpiRepo.deleteInBatch(kpiList);
			subscriptionKpiRepo.delete(kpiList);
			
			subscriptionKpiRepo.flush();
			
			saveSubscriptionKpis(request.getKpi(), subsID);
			logger.info("Updated KPIs for subscription : {}", subsID);
		}
		
		logger.info("OnticInfoServiceImpl::modifySubscription End");
	}
	
	@Transactional
	public void stopSubscription(StopSubscriptionRequest request) {
		logger.info("OnticInfoServiceImpl::stopSubscription Start");
		
		try {
			subscriptionRepo.delete(request.getSubsID());
			logger.info("Deleted Subscription... {}", request.getSubsID());
		} catch(EmptyResultDataAccessException e) {
			logger.error("No subscription found with subscription id : {}", request.getSubsID());
			throw e;
		}
		
		logger.info("OnticInfoServiceImpl::stopSubscription End");
	}
	
	@Transactional
	public void deliverDegredationReport(ReportRequest request, String sessionId) {
		logger.info("OnticInfoServiceImpl::deliverDegredationReport Start");
		
		//Save Session
		Session session = new Session();
		session.setId(sessionId);
		session.setDegradationReportId(request.getReportID());
		session.setSubscriptionId(request.getSubscriptionID());
		session.setFrequency(request.getFrequency());
		session.setLastNotification(new Timestamp(System.currentTimeMillis()));
		
		Session savedSession = sessionRepo.save(session);
		
		logger.info("Saved session with id : {}", sessionId);
		
		logger.info("OnticInfoServiceImpl::deliverDegredationReport End");
	}
	
	@Transactional
	public void deliverInitialDegredationReport(ReportRequest request, String sessionId) {
		logger.info("OnticInfoServiceImpl::deliverDegredationReport Start");
		
		//Save Session
		Session session = new Session();
		session.setId(sessionId);
		session.setDegradationReportId(request.getReportID());
		session.setSubscriptionId(request.getSubscriptionID());
		session.setFrequency(request.getFrequency());
		session.setLastNotification(new Timestamp(System.currentTimeMillis()));
		
		Session savedSession = sessionRepo.save(session);
		
		logger.info("Saved session with id : {}", sessionId);
		
		//Save SessionKpi
		HashMap<String,ArrayList<ReportRequestKpiServiceValue>> services = request.getServices(); 
		
		if(services != null) {
			for(String service : services.keySet()) {
				ArrayList<ReportRequestKpiServiceValue> kpis = services.get(service);
				for (ReportRequestKpiServiceValue kpi : kpis){
					SessionKpi sessionKpi = new SessionKpi();
					SessionKpiPK pk = new SessionKpiPK();
					pk.setKpiId(kpi.getName());
					pk.setSessionId(savedSession.getId());
					sessionKpi.setId(pk);
					
					sessionKpi.setSessionId(savedSession.getId());
					sessionKpi.setKpiId(kpi.getName());
					
					sessionKpiRepo.save(sessionKpi);
					
					logger.info("Saved session kpi with session id : {} and kpi : {}", sessionId, kpi.getName());
				}
			}
		}
		
		//Save SessionLocation
		HashMap<String,String> locations = request.getLocations();
		
		if(locations != null) {
			for(String location : locations.keySet()) {
				SessionLocation sessionLoc = new SessionLocation();
				SessionLocationPK id = new SessionLocationPK();
				id.setLocationId(location);
				id.setSessionId(savedSession.getId());
				
				sessionLoc.setSessionLocationPK(id);
				
				sessionLoc.setSessionId(savedSession.getId());
				sessionLoc.setLocationId(location);
				
				sessionLocRepo.save(sessionLoc);
				
				logger.info("Saved session location with session id : {} and location id : {}", sessionId, location);
			}
		}
		
		
		logger.info("OnticInfoServiceImpl::deliverInitialDegredationReport End");
	}
	
	public void stopDegredationReportDeliveryConditions(StopDeliveryConditionsRequest request) {
		logger.info("OnticInfoServiceImpl::stopDegredationReportDeliveryConditions Start");
		
		Session session = sessionRepo.findById(request.getSessionID());
		
		session.setFrequency(-1);
		
		sessionRepo.save(session);
		
		logger.info("Updated the session frequency to -1 {}", request.getSessionID());
		
		logger.info("OnticInfoServiceImpl::stopDegredationReportDeliveryConditions End");
		
	}
	
	@Transactional
	public void modifyDegredationReportDeliveryConditions(ModifyDeliveryConditionsRequest request) {
		logger.info("OnticInfoServiceImpl::modifyDegredationReportDeliveryConditions Start");
		
		//1.Query Subscription_kpi records with KPIs, If we get any records
		//2.Remove the session_kpi with session and kpi and ddd new session_kpi
		//3.Similarly for locations
		
		DeliveryService service = request.getService();
		
		//TODO:Need to check the logic and test
		if(service != null && service.getKpi() != null) {
			for(String kpi : service.getKpi()) {
				List<SubscriptionKpi> subKpis = subscriptionKpiRepo.findByKpiId(kpi);
				if(subKpis != null && !subKpis.isEmpty()) {
					List<SessionKpi> sessionKpis = sessionKpiRepo.findBySessionIdAndKpiId(request.getSessionID(), kpi);
					//sessionKpiRepo.deleteInBatch(sessionKpis);
					sessionKpiRepo.delete(sessionKpis);
					
					saveSessionKpis(request.getSessionID(), new String[]{kpi});
				}
			}
		}
		
		
		//TODO:Need to check the logic and test
		if(request.getLocation() != null) {
			for(String location : request.getLocation()) {
				List<SubscriptionLocation> subLocs = subscriptionLocRepo.findByLocationId(location);
				
				if(subLocs != null && !subLocs.isEmpty()) {
					List<SessionLocation> sessionLocs = sessionLocRepo.findBySessionIdAndLocationId(request.getSessionID(), location);
					//sessionLocRepo.deleteInBatch(sessionLocs);
					sessionLocRepo.delete(sessionLocs);
					
					saveSessionLocations(request.getSessionID(), request.getLocation());
				}
			}
		}
		
		logger.info("OnticInfoServiceImpl::modifyDegredationReportDeliveryConditions End");
	}
	
	private void saveSubscriptionLocations(String[] locations, String subsID) {
		if(locations != null) {
			for(String location : locations) {
				Location resultLocation = locationRepo.findById(location);
				if(resultLocation == null) {
					throw new RuntimeException("Location does not exist "+location);
				} else {
					SubscriptionLocation subLoc = new SubscriptionLocation();
					SubscriptionLocationPK subLocPK = new SubscriptionLocationPK();
					
					subLocPK.setLocationId(resultLocation.getId());
					subLocPK.setSubscriptionId(subsID);
					subLoc.setSubscriptionLocationPK(subLocPK);
					
					subLoc.setLocationId(resultLocation.getId());
					subLoc.setSubscriptionId(subsID);
					
					subscriptionLocRepo.save(subLoc);
					logger.info("Saved Subscription-Location, subcriptionid : {}, locationid: {}", subsID, resultLocation.getId());
				}
							
			}
		}
	}
	
	private void saveSubscriptionKpis(String[] kpis, String subsID) {
		if(kpis != null) {
			for(String kpi : kpis) {
				Kpi resultKpi = kpiRepo.findById(kpi);
				if(resultKpi == null) {
					throw new RuntimeException("KPI does not exist "+kpi);
				} else {
					SubscriptionKpi subKpi = new SubscriptionKpi();
					SubscriptionKpiPK subKpiPK = new SubscriptionKpiPK();
					
					subKpiPK.setKpiId(kpi);
					subKpiPK.setSubscriptionId(new String(subsID));
					subKpi.setSubscriptionKpiPK(subKpiPK);
					
					subKpi.setKpiId(kpi);
					subKpi.setSubscriptionId(new String(subsID));
					
					subscriptionKpiRepo.save(subKpi);
					logger.info("Saved Subscription-Kpi, subcriptionid : {}, kpiid: {}", subsID, resultKpi.getId());
				}
				
			}
		}
	}
	
	private void saveSessionKpis(String sessionId, String[] kpiArr) {
		for(String kpi : kpiArr) {
			SessionKpi sessionKpi = new SessionKpi();
			SessionKpiPK pk = new SessionKpiPK();
			pk.setKpiId(kpi);
			pk.setSessionId(sessionId);
			sessionKpi.setId(pk);
			
			sessionKpi.setSessionId(sessionId);
			sessionKpi.setKpiId(kpi);
			
			sessionKpiRepo.save(sessionKpi);
			
			logger.info("Saved session kpi with session id : {} and kpi : {}", sessionId, kpi);
		}
	}
	
	private void saveSessionLocations(String sessionId, String[] locations) {
		for(String location : locations) {
			SessionLocation sessionLoc = new SessionLocation();
			SessionLocationPK id = new SessionLocationPK();
			id.setLocationId(location);
			id.setSessionId(sessionId);
			
			sessionLoc.setSessionLocationPK(id);
			
			sessionLoc.setSessionId(sessionId);
			sessionLoc.setLocationId(location);
			
			sessionLocRepo.save(sessionLoc);
			
			logger.info("Saved session location with session id : {} and location id : {}", sessionId, location);
		}
	}

}
