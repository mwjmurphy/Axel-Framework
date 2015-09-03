package org.xmlactions.common.copyright;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.ResourceUtils;

public class AddCopyrightToFiles {
	
	private static final Logger log = LoggerFactory.getLogger(AddCopyrightToFiles.class);
	
	
	private static final String copyrightNotice =
			"/*\n" +
			" * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>\n" +
			" *\n" +
			" * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
			" * you may not use this file except in compliance with the License.\n" +
			" * You may obtain a copy of the License at\n" +
			" *\n" +
			" *     http://www.apache.org/licenses/LICENSE-2.0\n" +
			" *\n" +
			" * Unless required by applicable law or agreed to in writing, software\n" +
			" * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
			" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or\n" + 
			" * implied. See the License for the specific language governing\n" + 
			" * permissions and limitations under the License.\n" +
			" */\n" +
			"\n";
	
	private static final String matchCopyrightPattern = "Copyright (C) Mike Murphy 2003-";
	
	
	/**
	 * Load files from a specific folder and add the copyright notice.
	 */
	@Test
	public void onlyOnce() {
		
		String sampleFileContent = "/*\n * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>\n *\n";
		
		if (sampleFileContent.contains(matchCopyrightPattern)) {
			
		} else {
			fail("Was not able to detect copyright pattern [" + matchCopyrightPattern +"] in [" + sampleFileContent + "]");
		}
		
	}

	/**
	 * Load files from a specific folder that already has the copyright notice.
	 * @throws IOException 
	 */
	@Test
	public void oneFileWithCopyright() throws IOException {
		
		String fileName = "/test/files/copyrightFile.java";
		
		String fileContent = ResourceUtils.loadFile(fileName);
		
		if (fileContent.contains(matchCopyrightPattern)) {
			
		} else {
			fail("Was not able to detect copyright pattern [" + matchCopyrightPattern +"] in [" + fileContent + "]");
		}
		
	}

	/**
	 * Load files from a specific folder that does not have the copyright notice.
	 * @throws IOException 
	 */
	@Test
	public void oneFileNoCopyright() throws IOException {
		
		String fileName = "/test/files/noCopyrightFile.java";
		
		String fileContent = ResourceUtils.loadFile(fileName);
		
		if (fileContent.contains(matchCopyrightPattern)) {
			fail("Was not able to detect copyright pattern [" + matchCopyrightPattern +"] in [" + fileContent + "]");
		} else {
		}
		
		fileContent = copyrightNotice + fileContent;
		log.debug("fileContent:" + fileContent);
		
	}

	/**
	 * Load files from a specific folder that does not have the copyright notice.
	 * @throws IOException 
	 */
	@Test
	public void recursiveCopy() throws IOException {
		
		//String folderName = "src/main/java";
		String folderName = "src/test/resources/test/files";
		
		// String folder = ResourceUtils.getResourceURL(folderName).getPath();
		File folder = new File(folderName);
		if (folder.isDirectory()) {
			processFolder(folder);
		} else {
			processFile(folder);
		}

	}
	
	/**
	 * Load files from a specific folder that does not have the copyright notice.
	 * @throws IOException 
	 */
	@Test
	public void RunRecursiveCopy() throws IOException {
		startProcess("../axel-action/src/main/java");
		startProcess("../axel-common/src/main/java");
		startProcess("../axel-db/src/main/java");
		startProcess("../axel-ee/src/main/java");
		startProcess("../axel-email/src/main/java");
		startProcess("../axel-mapping/src/main/java");
		startProcess("../axel-web/src/main/java");
		startProcess("../axel-web-db/src/main/java");
		startProcess("../axel-web-ee/src/main/java");
	}
	
	private void startProcess(String name) throws IOException {
		File folder = new File(name);
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				if (file.isDirectory()) {
					processFolder(file);
				} else {
					processFile(file);
				}
			}
		} else {
			processFile(folder);
		}
	}
	
	private void processFolder(File folder) throws IOException {
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				if (file.isDirectory()) {
					processFolder(file);
				} else {
					processFile(file);
				}
			}
		} else {
			processFile(folder);
		}
	}
	
	private void processFile(File file) throws IOException {
		if (file.isFile() && file.getName().endsWith(".java")) {
			log.debug("process java file:" + file.getAbsolutePath());
			String fileContent = ResourceUtils.loadFile(file.getAbsolutePath());
			fileContent = addCopyRightIfNotExists(fileContent);
			if (fileContent != null) {
				// FileUtils.writeStringToFile(file, fileContent);	// dont write out
			}
		}
	}

	private String addCopyRightIfNotExists(String fileContent) {
		
		if (fileContent.contains(matchCopyrightPattern)) {
			return null;
		} else {
			return copyrightNotice + fileContent;
		}
	}
}
