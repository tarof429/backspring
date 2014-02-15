package com.taro.backspring;

public class Configuration {

	private String name;

	private String sourceDirectory;

	private String targetDirectory;

	private BackupStrategyType backupStrategyType;

	private Filter filter;

	private String schedule;

	private boolean runNow;

	private String logging;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public String getTargetDirectory() {
		return targetDirectory;
	}

	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	public BackupStrategyType getBackupStrategyType() {
		return backupStrategyType;
	}

	public void setBackupStrategyType(BackupStrategyType backupStrategyType) {
		this.backupStrategyType = backupStrategyType;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public boolean isRunNow() {
		return runNow;
	}

	public void setRunNow(boolean runNow) {
		this.runNow = runNow;
	}

	public String getLogging() {
		return logging;
	}

	public void setLogging(String logging) {
		this.logging = logging;
	}

	@Override
	public String toString() {
		return "Configuration [name=" + name + ", sourceDirectory="
				+ sourceDirectory + ", targetDirectory=" + targetDirectory
				+ ", backupStrategyType=" + backupStrategyType + ", filter="
				+ filter + ", schedule=" + schedule + ", runNow=" + runNow
				+ ", logging=" + logging + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((backupStrategyType == null) ? 0 : backupStrategyType
						.hashCode());
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((logging == null) ? 0 : logging.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (runNow ? 1231 : 1237);
		result = prime * result
				+ ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result
				+ ((sourceDirectory == null) ? 0 : sourceDirectory.hashCode());
		result = prime * result
				+ ((targetDirectory == null) ? 0 : targetDirectory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (backupStrategyType != other.backupStrategyType)
			return false;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (logging == null) {
			if (other.logging != null)
				return false;
		} else if (!logging.equals(other.logging))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (runNow != other.runNow)
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (sourceDirectory == null) {
			if (other.sourceDirectory != null)
				return false;
		} else if (!sourceDirectory.equals(other.sourceDirectory))
			return false;
		if (targetDirectory == null) {
			if (other.targetDirectory != null)
				return false;
		} else if (!targetDirectory.equals(other.targetDirectory))
			return false;
		return true;
	}
}
