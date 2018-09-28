package snmaddula.ngsbatch.app.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;

import snmaddula.ngsbatch.app.model.AccountDetails;
import snmaddula.ngsbatch.app.repo.AccountDetailsRepo;

/**
 * 
 * @author snmaddula
 *
 */
@Configuration
@EnableBatchProcessing
public class AppBatchConfig {

	private JobBuilderFactory jobs;
	private StepBuilderFactory steps;

	public AppBatchConfig(JobBuilderFactory jobs, StepBuilderFactory steps) {
		this.jobs = jobs;
		this.steps = steps;
	}

	@Bean
	public Job alphaJob(Step alphaStep) {
		return jobs.get("alphaJob").start(alphaStep).build();
	}

	@Bean
	@JobScope
	public Step alphaStep(ItemStreamReader<AccountDetails> accountDetailsReader,
			ItemWriter<AccountDetails> accountDetailsWriter) {
		return steps.get("alphaStep").<AccountDetails, AccountDetails>chunk(1).reader(accountDetailsReader)
				.writer(accountDetailsWriter).build();
	}

	@Bean
	@StepScope
	public ItemStreamReader<AccountDetails> accountDetailsReader(BatchExecutionData batchExecutionData)
			throws IOException {
		return new PoiItemReader<AccountDetails>() {
			{
				setLinesToSkip(1);
				setResource(new InputStreamResource(new PushbackInputStream(new FileInputStream(batchExecutionData.getFileAbsolutePath()))));
				setRowMapper(new RowMapper<AccountDetails>() {

					@Override
					public AccountDetails mapRow(RowSet rs) throws Exception {
						AccountDetails alpha = new AccountDetails();
						alpha.setCustomerId(Double.valueOf(rs.getColumnValue(0)).intValue());
						alpha.setCustomerName(rs.getColumnValue(1));
						alpha.setEmailId(rs.getColumnValue(2));
						alpha.setAccountOpenDate(new Date(Long.valueOf(rs.getColumnValue(3))));
						alpha.setBalance(BigDecimal.valueOf(Double.valueOf(rs.getColumnValue(4))));
						return alpha;
					}
				});
			}
		};
	}

	@Bean
	@StepScope
	public ItemWriter<AccountDetails> accountDetailsWriter(AccountDetailsRepo accountDetailsRepo) {
		return (items) -> accountDetailsRepo.saveAll(items);
	}

}
