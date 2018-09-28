package snmaddula.ngsbatch.app.config;

import org.springframework.stereotype.Component;

/**
 * 
 * @author snmaddula
 *
 */
@Component
public class BatchExecutionData {

	private String fileAbsolutePath;

	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}

	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}
	
	
}
