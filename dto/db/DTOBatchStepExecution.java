package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BATCH_STEP_EXECUTION", schema = "INSA1")
public class DTOBatchStepExecution implements Serializable {

	private static final long serialVersionUID = 5802457925302353802L;

	@Id
	@NotNull
	@Column(name = "STEP_EXECUTION_ID", nullable = false, precision = 19, scale = 0)
	private Integer stepExecutionId;

	@NotNull
	@Column(name = "VERSION", nullable = false, precision = 19, scale = 0)
	private Integer version;

	@NotNull
	@Column(name = "STEP_NAME", nullable = false, length = 100)
	private String stepName;

	@NotNull
	@Column(name = "JOB_EXECUTION_ID", nullable = false, precision = 19, scale = 0)
	private Integer jobExecutionId;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false)
	private Date createTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = true)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = true)
	private Date endTime;

	@Column(name = "STATUS", nullable = true, length = 10)
	private String status;

	@Column(name = "COMMIT_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer commitCount;

	@Column(name = "READ_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer readCount;

	@Column(name = "FILTER_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer filterCount;

	@Column(name = "WRITE_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer writeCount;

	@Column(name = "READ_SKIP_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer readSkipCount;

	@Column(name = "WRITE_SKIP_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer writeSkipCount;

	@Column(name = "PROCESS_SKIP_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer processSkipCount;

	@Column(name = "ROLLBACK_COUNT", nullable = true, precision = 19, scale = 0)
	private Integer rollBackCount;

	@Column(name = "EXIT_CODE", nullable = true, length = 2500)
	private String exitCode;

	@Column(name = "EXIT_MESSAGE", nullable = true, length = 2500)
	private String exitMessage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED", nullable = true)
	private Date lastUpdated;

	@ManyToOne
	@JoinColumn(name = "JOB_EXECUTION_ID", nullable = true, insertable = false, updatable = false)
	private DTOBatchJobExecution batchJobExecution;

	public DTOBatchStepExecution() {
		super();
	}

	public Integer getStepExecutionId() {
		return stepExecutionId;
	}

	public void setStepExecutionId(Integer stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Integer getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Integer jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(Integer commitCount) {
		this.commitCount = commitCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(Integer filterCount) {
		this.filterCount = filterCount;
	}

	public Integer getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(Integer writeCount) {
		this.writeCount = writeCount;
	}

	public Integer getReadSkipCount() {
		return readSkipCount;
	}

	public void setReadSkipCount(Integer readSkipCount) {
		this.readSkipCount = readSkipCount;
	}

	public Integer getWriteSkipCount() {
		return writeSkipCount;
	}

	public void setWriteSkipCount(Integer writeSkipCount) {
		this.writeSkipCount = writeSkipCount;
	}

	public Integer getProcessSkipCount() {
		return processSkipCount;
	}

	public void setProcessSkipCount(Integer processSkipCount) {
		this.processSkipCount = processSkipCount;
	}

	public Integer getRollBackCount() {
		return rollBackCount;
	}

	public void setRollBackCount(Integer rollBackCount) {
		this.rollBackCount = rollBackCount;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitMessage() {
		return exitMessage;
	}

	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public DTOBatchJobExecution getBatchJobExecution() {
		return batchJobExecution;
	}

	public void setBatchJobExecution(DTOBatchJobExecution batchJobExecution) {
		this.batchJobExecution = batchJobExecution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobExecutionId == null) ? 0 : jobExecutionId.hashCode());
		result = prime * result + ((stepExecutionId == null) ? 0 : stepExecutionId.hashCode());
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
		DTOBatchStepExecution other = (DTOBatchStepExecution) obj;
		if (jobExecutionId == null) {
			if (other.jobExecutionId != null)
				return false;
		} else if (!jobExecutionId.equals(other.jobExecutionId))
			return false;
		if (stepExecutionId == null) {
			if (other.stepExecutionId != null)
				return false;
		} else if (!stepExecutionId.equals(other.stepExecutionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOBatchStepExecution [stepExecutionId=" + stepExecutionId + ", version=" + version + ", stepName="
				+ stepName + ", jobExecutionId=" + jobExecutionId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", status=" + status + ", commitCount=" + commitCount + ", readCount=" + readCount + ", filterCount="
				+ filterCount + ", writeCount=" + writeCount + ", readSkipCount=" + readSkipCount + ", writeSkipCount="
				+ writeSkipCount + ", processSkipCount=" + processSkipCount + ", rollBackCount=" + rollBackCount
				+ ", exitCode=" + exitCode + ", exitMessage=" + exitMessage + ", lastUpdated=" + lastUpdated
				+ ", batchJobExecution=" + batchJobExecution + "]";
	}

}
