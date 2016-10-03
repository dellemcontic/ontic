package com.emc.ontic.ms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emc.ontic.ms.OnticInfoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnticInfoApplication.class)
@WebAppConfiguration
public class OnticInfoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
