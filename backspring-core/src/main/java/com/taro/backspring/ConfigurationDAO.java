package com.taro.backspring;

public interface ConfigurationDAO {

	String getConfigFile();

	void save(Configuration configuration) throws ConfigurationDAOException;

	Configuration load() throws ConfigurationDAOException;
}
