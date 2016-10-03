package ontic.af.analytic;

import org.springframework.messaging.Message;


public interface AnalyticFunctionIF {

	void analyze (Message<byte[]> message);
}
