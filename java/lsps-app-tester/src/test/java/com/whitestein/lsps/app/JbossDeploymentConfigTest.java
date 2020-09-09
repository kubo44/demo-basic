package com.whitestein.lsps.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.whitestein.lsps.common.Strings;
import com.whitestein.lsps.common.test.PackagingTestHelper;

/**
 * JBoss requires that all modules are listed in the file jboss-deployment-structure.xml
 */
public class JbossDeploymentConfigTest {

	/**
	 * Fails if jboss-deployment-structure.xml does not contain sub-deployment definition for all ejb and web modules defined 
	 * in the pom file of the application ear project.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeploymentStructure() throws Exception {
		Path jbossDeploymentStructureFilePath = Paths.get("..", "..", "lsps-app", "lsps-app-ear", "src", "main", "application", "META-INF", "jboss-deployment-structure.xml");

		String jbossFileString = readFile(jbossDeploymentStructureFilePath, StandardCharsets.UTF_8);

		File targetDir = Paths.get("..", "..", "lsps-app", "lsps-app-ear", "target").toFile().getCanonicalFile();
		File[] earFiles = targetDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ear");
			}
		});
		assertNotNull(earFiles);
		assertEquals("Invalid number of ear files", 1, earFiles.length);

		File earFile = earFiles[0];

		PackagingTestHelper helper = new PackagingTestHelper();
		helper.loadFilesForPattern(earFile, "^[-_.A-Za-z0-9]*\\.(war|jar)");

		List<String> patterns = new ArrayList<>();
		String pattern = "^.*<sub-deployment.*name=\"(.*)\">";
		Pattern r = Pattern.compile(pattern, Pattern.MULTILINE);
		Matcher m = r.matcher(jbossFileString);
		while (m.find()) {
			patterns.add(m.group(1).trim());
		}

		helper.addPatterns(patterns);

		checkPatterns(helper);

	}

	private void checkPatterns(PackagingTestHelper helper) {
		List<String> invalidFiles = helper.getInvalidFiles();
		List<String> missing = helper.getInvalidPatterns();

		if (!invalidFiles.isEmpty() || !missing.isEmpty()) {

			Collections.sort(invalidFiles);
			Collections.sort(missing);

			StringBuilder sb = new StringBuilder();

			if (!invalidFiles.isEmpty()) {
				sb.append("Missing subdeployment for: ");
				sb.append(Strings.join(invalidFiles, ", "));
				sb.append(".");
			}

			if (!invalidFiles.isEmpty() && !missing.isEmpty()) {
				sb.append(" ");
			}

			if (!missing.isEmpty()) {
				sb.append("Unexpected subdeployment for: ");
				sb.append(Strings.join(missing, ", "));
				sb.append(".");
			}

			fail(sb.toString());
		}
	}

	private String readFile(Path path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, encoding);
	}

}
