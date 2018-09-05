package com.ranhfun.price.services;

import java.util.List;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.slf4j.Logger;

import com.ranhfun.price.PriceConstants;
import com.ranhfun.price.jdbc.JDBCPersistentAdapter;

public class PriceModule {

	@EagerLoad
	public PriceService buildPriceService(@Symbol(PriceConstants.PRICE_TRANSACTION_ISOLATION) int transactionIsolation,
			Logger logger,
			List<JDBCPersistentAdapter> contributions) {
		return new PriceServiceImpl(transactionIsolation, logger, contributions);
	}
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
    	configuration.add(PriceConstants.PRICE_TRANSACTION_ISOLATION, "1");
        configuration.add(PriceConstants.PRICE_CREATE_TABLE_STARTUP, "true");
    }
	
}
