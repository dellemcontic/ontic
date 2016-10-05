package com.emc.ontic.dashboard;

import java.io.IOException;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

public class LocalFileSystemMessageWriter extends AbstractWriter{

	@SendTo("/topic/message")
	public FlowsMessage write(Message<byte[]> message) throws IOException {
		return writeToFile(new String((byte[])message.getPayload()));
	}
}
