package com.pivotal.tstat.ingester;

import org.springframework.messaging.Message;

import com.pivotal.tstat.tcp.TstatException;

public interface MessageTransformer {

	public Message<byte[]> transform(Message<?> message) throws TstatException;
}
