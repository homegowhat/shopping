package com.shopping.web.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import java.util.Hashtable;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.Name;
import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;

public class ProxoolDataSourceImpl implements DataSource {
	private ProxoolDataSource proxy;
	
	public ProxoolDataSourceImpl() {
		proxy = new ProxoolDataSource();
	}

	public boolean equals(Object obj) {
		return proxy.equals(obj);
	}

	public String getAlias() {
		return proxy.getAlias();
	}

	public Connection getConnection() throws SQLException {
		return proxy.getConnection();
	}

	public Connection getConnection(String s, String s1) throws SQLException {
		return proxy.getConnection(s, s1);
	}

	public String getDriver() {
		return proxy.getDriver();
	}

	public String getDriverUrl() {
		return proxy.getDriverUrl();
	}

	public String getFatalSqlExceptionsAsString() {
		return proxy.getFatalSqlExceptionsAsString();
	}

	public String getFatalSqlExceptionWrapperClass() {
		return proxy.getFatalSqlExceptionWrapperClass();
	}

	public int getHouseKeepingSleepTime() {
		return (int)proxy.getHouseKeepingSleepTime();
	}

	public String getHouseKeepingTestSql() {
		return proxy.getHouseKeepingTestSql();
	}

	public String getJmxAgentId() {
		return proxy.getJmxAgentId();
	}

	public int getLoginTimeout() throws SQLException {
		return proxy.getLoginTimeout();
	}

	public PrintWriter getLogWriter() throws SQLException {
		return proxy.getLogWriter();
	}

	public int getMaximumActiveTime() {
		return (int)proxy.getMaximumActiveTime();
	}

	public int getMaximumConnectionCount() {
		return proxy.getMaximumConnectionCount();
	}

	public int getMaximumConnectionLifetime() {
		return (int)proxy.getMaximumConnectionLifetime();
	}

	public int getMinimumConnectionCount() {
		return proxy.getMinimumConnectionCount();
	}

	public Object getObjectInstance(Object refObject, Name name, Context context, Hashtable hashtable) throws Exception {
		return proxy.getObjectInstance(refObject, name, context, hashtable);
	}

	public int getOverloadWithoutRefusalLifetime() {
		return (int)proxy.getOverloadWithoutRefusalLifetime();
	}

	public String getPassword() {
		return proxy.getPassword();
	}

	public int getPrototypeCount() {
		return proxy.getPrototypeCount();
	}

	public int getRecentlyStartedThreshold() {
		return (int)proxy.getRecentlyStartedThreshold();
	}

	public int getSimultaneousBuildThrottle() {
		return proxy.getSimultaneousBuildThrottle();
	}

	public String getStatistics() {
		return proxy.getStatistics();
	}

	public String getStatisticsLogLevel() {
		return proxy.getStatisticsLogLevel();
	}

	public String getUser() {
		return proxy.getUser();
	}

	public int hashCode() {
		return proxy.hashCode();
	}

	public boolean isJmx() {
		return proxy.isJmx();
	}

	public boolean isTestAfterUse() {
		return proxy.isTestAfterUse();
	}

	public boolean isTestBeforeUse() {
		return proxy.isTestBeforeUse();
	}

	public boolean isTrace() {
		return proxy.isTrace();
	}

	public boolean isVerbose() {
		return proxy.isVerbose();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
//		return proxy.isWrapperFor(iface);
		return true;
	}

	public void setAlias(String alias) {
		proxy.setAlias(alias);
	}

	public void setDelegateProperties(String arg0) {
		proxy.setDelegateProperties(arg0);
	}

	public void setDriver(String driver) {
		proxy.setDriver(driver);
	}

	public void setDriverUrl(String url) {
		proxy.setDriverUrl(url);
	}

	public void setFatalSqlExceptionsAsString(String fatalSqlExceptionsAsString) {
		proxy.setFatalSqlExceptionsAsString(fatalSqlExceptionsAsString);
	}

	public void setFatalSqlExceptionWrapperClass(String fatalSqlExceptionWrapperClass) {
		proxy.setFatalSqlExceptionWrapperClass(fatalSqlExceptionWrapperClass);
	}

	public void setHouseKeepingSleepTime(int houseKeepingSleepTime) {
		proxy.setHouseKeepingSleepTime(houseKeepingSleepTime);
	}

	public void setHouseKeepingTestSql(String houseKeepingTestSql) {
		proxy.setHouseKeepingTestSql(houseKeepingTestSql);
	}

	public void setJmx(boolean jmx) {
		proxy.setJmx(jmx);
	}

	public void setJmxAgentId(String jmxAgentId) {
		proxy.setJmxAgentId(jmxAgentId);
	}

	public void setLoginTimeout(int loginTimeout) throws SQLException {
		proxy.setLoginTimeout(loginTimeout);
	}

	public void setLogWriter(PrintWriter logWriter) throws SQLException {
		proxy.setLogWriter(logWriter);
	}

	public void setMaximumActiveTime(int maximumActiveTime) {
		proxy.setMaximumActiveTime(maximumActiveTime);
	}

	public void setMaximumConnectionCount(int maximumConnectionCount) {
		proxy.setMaximumConnectionCount(maximumConnectionCount);
	}

	public void setMaximumConnectionLifetime(int maximumConnectionLifetime) {
		proxy.setMaximumConnectionLifetime(maximumConnectionLifetime);
	}

	public void setMinimumConnectionCount(int minimumConnectionCount) {
		proxy.setMinimumConnectionCount(minimumConnectionCount);
	}

	public void setOverloadWithoutRefusalLifetime(int overloadWithoutRefusalLifetime) {
		proxy.setOverloadWithoutRefusalLifetime(overloadWithoutRefusalLifetime);
	}

	public void setPassword(String password) {
		proxy.setPassword(password);
	}

	public void setPrototypeCount(int prototypeCount) {
		proxy.setPrototypeCount(prototypeCount);
	}

	public void setRecentlyStartedThreshold(int recentlyStartedThreshold) {
		proxy.setRecentlyStartedThreshold(recentlyStartedThreshold);
	}

	public void setSimultaneousBuildThrottle(int simultaneousBuildThrottle) {
		proxy.setSimultaneousBuildThrottle(simultaneousBuildThrottle);
	}

	public void setStatistics(String statistics) {
		proxy.setStatistics(statistics);
	}

	public void setStatisticsLogLevel(String statisticsLogLevel) {
		proxy.setStatisticsLogLevel(statisticsLogLevel);
	}

	public void setTestAfterUse(boolean testAfterUse) {
		proxy.setTestAfterUse(testAfterUse);
	}

	public void setTestBeforeUse(boolean testBeforeUse) {
		proxy.setTestBeforeUse(testBeforeUse);
	}

	public void setTrace(boolean trace) {
		proxy.setTrace(trace);
	}

	public void setUser(String user) {
		proxy.setUser(user);
	}

	public void setVerbose(boolean verbose) {
		proxy.setVerbose(verbose);
	}

	public String toString() {
		return proxy.toString();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
//		return proxy.<T>unwrap(iface);
		return null;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	
	
}
