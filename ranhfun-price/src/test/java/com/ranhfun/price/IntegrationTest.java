package com.ranhfun.price;

import java.io.IOException;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ranhfun.price.data.AgentInfo;
import com.ranhfun.price.data.NumberInfo;
import com.ranhfun.price.services.AppModule;
import com.ranhfun.price.services.PriceModule;
import com.ranhfun.price.services.PriceService;

public class IntegrationTest {

	private PriceService priceService;

	private Registry registry;

	@BeforeClass
	public void setup() throws IOException {
		setup_registry(PriceModule.class, AppModule.class);
		priceService = registry.getService(PriceService.class);
		priceService.doCreateTables();
	}

	public void setup_registry(Class<?>... moduleClasses) {
		RegistryBuilder builder = new RegistryBuilder();
		builder.add(moduleClasses);
		registry = builder.build();
		registry.performRegistryStartup();
	}

	@AfterClass
	public final void shutdown_registry() {
		registry.cleanupThread();
		registry.shutdown();
		registry = null;
	}

	@AfterClass
	public void close_process_engine() throws IOException {
		priceService.doRemoveAllAgentInfos();
		priceService.doRemoveAllNumberInfos();
		priceService.doDropTables();
	}

	@Test
	public void test_agent() throws IOException {
		Assert.assertEquals(priceService.doGetLastPriceInfoSequenceId(), 0);
		AgentInfo agentInfo = new AgentInfo();
		agentInfo.setPriceType("A1");
		agentInfo.setAgentType("B1");
		agentInfo.setAgentPoint(1.1D);
		priceService.doAddAgentInfo(agentInfo);
		Assert.assertEquals(priceService.doGetAgentInfoCount(), 1);
		Assert.assertEquals(priceService.doGetLastPriceInfoSequenceId(), 1);
		agentInfo.setAgentPoint(2.0D);
		priceService.doSetAgentInfo(agentInfo);
		Assert.assertEquals(priceService.doGetAgentInfo(agentInfo.getId()), agentInfo);
		Assert.assertEquals(priceService.doGetAgentInfo(agentInfo.getId()).getAgentPoint(), agentInfo.getAgentPoint());
		agentInfo.setAgentType("B2");
		priceService.doAddAgentInfo(agentInfo);
		Assert.assertEquals(priceService.doGetAgentInfoCount(), 2);
		Assert.assertEquals(priceService.doExistsAgentInfo("A1", "B2"), true);
		Assert.assertEquals(priceService.doGetAgentInfoByPriceType("A1").length, 2);
		priceService.doRemoveAgentInfo(agentInfo.getId());
		Assert.assertEquals(priceService.doExistsAgentInfo("A1", "B2"), false);
		Assert.assertEquals(priceService.doExistsAgentInfo("B1"), true);
		Assert.assertEquals(priceService.doGetAgentInfoCount(), 1);
		Assert.assertEquals(priceService.doCalculateAgentInfo("A1", "B1", 100D), 200D);
		Assert.assertEquals(priceService.doGetAgentInfoByPriceType("A1").length, 1);
	}

	@Test
	public void test_number() throws IOException {
		NumberInfo numberInfo = new NumberInfo();
		numberInfo.setPriceType("A1");
		numberInfo.setTotalNumber(500D);
		numberInfo.setTotalPoint(1.1D);
		priceService.doAddNumberInfo(numberInfo);
		Assert.assertEquals(priceService.doGetNumberInfoCount(), 1);
		numberInfo.setTotalPoint(2.0D);
		priceService.doSetNumberInfo(numberInfo);
		Assert.assertEquals(priceService.doGetNumberInfo(numberInfo.getId()), numberInfo);
		Assert.assertEquals(priceService.doGetNumberInfo(numberInfo.getId()).getTotalPoint(), numberInfo.getTotalPoint());
		numberInfo.setPriceType("B2");
		priceService.doAddNumberInfo(numberInfo);
		Assert.assertEquals(priceService.doGetNumberInfoCount("B2"), 1);
		Assert.assertEquals(priceService.doGetNumberInfoCount(), 2);
		priceService.doRemoveNumberInfo(numberInfo.getId());
		Assert.assertEquals(priceService.doGetNumberInfoCount(), 1);
		Assert.assertEquals(priceService.doCalculateNumberInfo("A1", 500D, 100D), 200D);
	}

}
