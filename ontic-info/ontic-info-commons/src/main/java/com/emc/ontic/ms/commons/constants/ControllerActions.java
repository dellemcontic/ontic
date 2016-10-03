package com.emc.ontic.ms.commons.constants;

public interface ControllerActions {
	
	String START_SUBSCRIPTION_ACTION = "/subscriptions";
	
	String MODIFY_SUBSCRIPTION_ACTION = "/subscriptions/{subsid}";
	
	String STOP_SUBSCRIPTION_ACTION = "/subscriptions/{subsid}";
	
	String MODIFY_DELIVERY_CONDITIONS_ACTION = "/sessions/{sessionid}";
	
	String STOP_DELIVERY_CONDITIONS_ACTION = "/sessions/{sessionid}";

}
