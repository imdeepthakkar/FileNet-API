package com.ibm.filenet.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author 10121125
 *
 */
public final class FileNetConfig implements FileNetConstants {
	private static final Logger LOG = Logger.getLogger(FileNetConfig.class);
	private static FileNetConfig configuration_instance = null;
	private static ResourceBundle rb = null;
	// private static ResourceBundle rb_2=null;

	/**
	 * Singleton class for getting properties across AMS application.
	 * 
	 * @return
	 * @throws CommonException
	 */
	public static FileNetConfig getInstance() {
		if (configuration_instance == null) {
			configuration_instance = new FileNetConfig();
			return configuration_instance;
		} else {
			return configuration_instance;
		}
	}

	/**
	 * 
	 * Constructor is called only once so loads the file on first and only
	 * instance.
	 * 
	 * @throws CommonException
	 */
	private FileNetConfig() {
		LOG.info("Constructor");
		initializer();
	}

	/**
	 * 
	 * Code to load properties bundle in resources package.
	 * 
	 * @throws CommonException
	 */
	public final void initializer() {
		LOG.info("Initializing properties file");
		try {
			rb = ResourceBundle.getBundle(FILENET_CONFIG_RESOURCE_FILE);
			LOG.info("Property file is being read : FileNetConfig.properties");
		} catch (Exception ex) {
			LOG.error("Properties file,IO Exception", ex);
		}
	}

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 * @throws CommonException
	 */

	public final String getConfiguration(String key) {
		try {
			return rb.getString(key.trim());
		} catch (MissingResourceException ex) {
			return "";
		} catch (Exception ex) {
			LOG.fatal("Properties file,IO Exception", ex);

		}
		return "";
	}
}
