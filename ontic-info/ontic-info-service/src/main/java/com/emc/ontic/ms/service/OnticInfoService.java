package com.emc.ontic.ms.service;

import com.emc.ontic.ms.commons.domain.ModifyDeliveryConditionsRequest;
import com.emc.ontic.ms.commons.domain.ModifySubscriptionRequest;
import com.emc.ontic.ms.commons.domain.ReportRequest;
import com.emc.ontic.ms.commons.domain.StartSubscriptionRequest;
import com.emc.ontic.ms.commons.domain.StopDeliveryConditionsRequest;
import com.emc.ontic.ms.commons.domain.StopSubscriptionRequest;

public interface OnticInfoService {
	
	void startSubscription(StartSubscriptionRequest request) throws Exception;
	
	void modifySubscription(ModifySubscriptionRequest request);
	
	void stopSubscription(StopSubscriptionRequest request);
	
	void deliverInitialDegredationReport(ReportRequest request, String sessionId);
	
	void deliverDegredationReport(ReportRequest request, String sessionId);
	
	void stopDegredationReportDeliveryConditions(StopDeliveryConditionsRequest request);
	
	void modifyDegredationReportDeliveryConditions(ModifyDeliveryConditionsRequest request);

}
