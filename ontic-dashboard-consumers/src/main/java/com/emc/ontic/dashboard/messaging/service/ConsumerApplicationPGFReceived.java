package com.emc.ontic.dashboard.messaging.service;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.stereotype.Service;


@SpringBootApplication
@EnableRabbit
public class ConsumerApplicationPGFReceived implements RabbitListenerConfigurer {
	
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplicationPGFReceived.class, args);
	}

	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}

	@Bean
	public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(jackson2Converter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
	}

}


@Service
class ReceiverPGFReceive {
	
	private String PGFReceiveFile;

	public String getPGFReceiveFile() {
		return PGFReceiveFile;
	}

	@Value("${files.PgfNotificationReceivedFile}")
	//@Value("#{systemEnvironment['RECEIVED_NOT_PATH']}")
	public void setPGFReceiveFile(String PGFReceiveFile) {
		this.PGFReceiveFile = PGFReceiveFile;
	}
	
	@RabbitListener(queues = "${queue.NotificationReceived}")
	//@RabbitListener(queues = "#{systemEnvironment['QUEUE_NOT_RECEIVED']}")
	public void receiveMessage(Message message) {
		try{

			JSONObject obj=new JSONObject(new String(message.getBody()));
			JSONParser parser = new JSONParser();
			
			System.out.println("NotificationReceived()::leido mensaje desde la cola: "+ new String(message.getBody()));
			
	
			// Parseo Mensaje
			JSONArray a = (JSONArray) parser.parse(new FileReader(PGFReceiveFile));
			while(a.size()>=10){
				a.remove(a.size()-10);
			}
			a.add(obj);
			FileWriter file1 = new FileWriter(PGFReceiveFile);
			
			System.out.println("NotificationReceived()::Escrito mensaje: "+ PGFReceiveFile);
			
			file1.write(a.toJSONString());
			file1.close();
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
	



