package snmaddula.ngsbatch.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import snmaddula.ngsbatch.app.config.BatchExecutionData;

/**
 * 
 * @author snmaddula
 *
 */
@RestController
public class AppController {

	private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

	@Value("${app.file-store-location}")
	private String localFileStore;

	private Job alphaJob;
	private JobLauncher jobLauncher;
	private BatchExecutionData batchExecutionData;
	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	public AppController(Job alphaJob, JobLauncher jobLauncher, BatchExecutionData batchExecutionData) {
		this.alphaJob = alphaJob;
		this.jobLauncher = jobLauncher;
		this.batchExecutionData = batchExecutionData;
	}

	/**
	 * to create directory structure if it doesn't exist
	 */
	@PostConstruct
	public void initFileSystem() {
		File dir = new File(localFileStore);
		dir.mkdirs();
	}

	@PostMapping("/launch")
	public ResponseEntity<String> launchJob(@RequestParam("file") MultipartFile file) throws Exception {

		LOG.info("Received file : {} ", file.getOriginalFilename());

		String localFilePath = localFileStore + file.getOriginalFilename();

		Files.write(Paths.get(localFilePath), file.getBytes(), StandardOpenOption.CREATE);

		batchExecutionData.setFileAbsolutePath(localFilePath);

		EXECUTOR.submit(() ->
			jobLauncher.run(alphaJob, new JobParametersBuilder().addString("FILE_NAME", localFilePath)
				.addDate("RUN_DATE", new Date()).toJobParameters()));

		return ResponseEntity.ok("JOB LAUNCHED!");
	}
}
