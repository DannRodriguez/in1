package mx.ine.procprimerinsa.batch.deciders;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class PrimeraInsaUpdateLNDecider implements JobExecutionDecider {
		
	public @NonNull FlowExecutionStatus decide(@NonNull JobExecution jobExecution, @Nullable StepExecution stepExecution) {
		Integer idCorteLNAActualizar = jobExecution.getJobParameters().getLong("idCorteLNAActualizar").intValue();
		
        return new FlowExecutionStatus(idCorteLNAActualizar.equals(0) ? "INSACULACION": "INSACULACION_UPDATE_LN");
    }

}
