package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BATCH_JOB_EXECUTION", schema = "INSA1")
public class DTOBatchJobExecution implements Serializable {

	private static final long serialVersionUID = -6679510005705306228L;

	@Id
	@NotNull
	@Column(name = "JOB_EXECUTION_ID", nullable = false, precision = 19, scale = 0)
	private Integer jobExecutionId;

	@Column(name = "VERSION", nullable = true, precision = 19, scale = 0)
	private Integer version;

	@NotNull
	@Column(name = "JOB_INSTANCE_ID", nullable = false, precision = 19, scale = 0)
	private Integer jobInstanceId;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = true)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = true)
	private Date endTime;

	@Column(name = "STATUS", nullable = true, length = 10)
	private String status;

	@Column(name = "EXIT_CODE", nullable = true, length = 2500)
	private String exitCode;

	@Column(name = "EXIT_MESSAGE", nullable = true, length = 2500)
	private String exitMessage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED", nullable = true)
	private Date lastUpdated;

	@OneToMany(mappedBy = "batchJobExecution", fetch = FetchType.EAGER)
	private List<DTOBatchStepExecution> steps;

	public DTOBatchJobExecution() {
		super();
	}

	public Integer getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Integer jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(Integer jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
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

	public List<DTOBatchStepExecution> getSteps() {
		return steps;
	}

	public void setSteps(List<DTOBatchStepExecution> steps) {
		this.steps = steps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobExecutionId == null) ? 0 : jobExecutionId.hashCode());
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
		DTOBatchJobExecution other = (DTOBatchJobExecution) obj;
		if (jobExecutionId == null) {
			if (other.jobExecutionId != null)
				return false;
		} else if (!jobExecutionId.equals(other.jobExecutionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOBatchJobExecution [jobExecutionId=" + jobExecutionId + ", version=" + version + ", jobInstanceId="
				+ jobInstanceId + ", createTime=" + createTime + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", status=" + status + ", exitCode=" + exitCode + ", exitMessage=" + exitMessage + ", lastUpdated="
				+ lastUpdated + ", steps=" + steps + "]";
	}

}
