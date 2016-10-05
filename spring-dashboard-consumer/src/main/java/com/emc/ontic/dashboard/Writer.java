package com.emc.ontic.dashboard;

import java.io.IOException;

import org.springframework.messaging.Message;

public interface Writer {

	FlowsMessage write (Message<byte[]> message) throws IOException;
}
