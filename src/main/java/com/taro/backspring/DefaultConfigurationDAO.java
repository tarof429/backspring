package com.taro.backspring;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component("configDAO")
public class DefaultConfigurationDAO implements ConfigurationDAO {

	private String configFile = "config.yml";

	private Logger logger = LoggerFactory
			.getLogger(DefaultConfigurationDAO.class);

	public DefaultConfigurationDAO() {

	}

	public String getConfigFile() {
		configFile = System.getProperty("basedir", ".") + "/conf/config.yml";

		return configFile;
	}

	public Configuration load() throws ConfigurationDAOException {
		Yaml yaml = new Yaml();
		try {
			logger.info("Loading configuration");

			File f = new File(getConfigFile());

			if (!f.exists()) {
				throw new ConfigurationDAOException("Unable to load "
						+ f.getAbsolutePath());
			}

			Object o = yaml.load(new FileReader(getConfigFile()));
			if (o instanceof Configuration) {
				return (Configuration) o;
			} else {
				throw new ConfigurationDAOException();
			}
		} catch (Exception e) {
			throw new ConfigurationDAOException(e);
		}
	}

	public void save(Configuration configuration)
			throws ConfigurationDAOException {
		if (!(configuration instanceof Configuration)
				|| (configuration == null)) {
			throw new ConfigurationDAOException();
		}
		Yaml yaml = new Yaml();
		try {
			logger.info("Saving configuration");
			yaml.dump(configuration, new FileWriter(getConfigFile()));
		} catch (Exception e) {
			throw new ConfigurationDAOException(e);
		}
	}
}
