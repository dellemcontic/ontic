package com.pivotal.tstat.writer;

import org.springframework.messaging.Message;

import com.pivotal.tstat.tcp.TstatException;

public interface Writer {

	void write (Message<byte[]> message) throws TstatException;
}
