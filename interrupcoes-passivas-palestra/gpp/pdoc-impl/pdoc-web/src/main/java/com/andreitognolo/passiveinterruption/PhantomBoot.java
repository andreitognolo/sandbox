package com.andreitognolo.passiveinterruption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomBoot {

	private static final Logger LOG = LoggerFactory.getLogger(PhantomBoot.class);

	public void boot() {
		PhantomHelper.me().boot();
	}

	public void shutdown() {
		try {
			PhantomHelper.me().shutdown();
		} catch (Exception e) {
			LOG.error("error phantom shutdown", e);
		}
	}

}
