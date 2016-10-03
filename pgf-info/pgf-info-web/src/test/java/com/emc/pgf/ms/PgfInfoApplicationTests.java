package com.emc.pgf.ms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emc.pgf.ms.PgfInfoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PgfInfoApplication.class)
@WebAppConfiguration
public class PgfInfoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
