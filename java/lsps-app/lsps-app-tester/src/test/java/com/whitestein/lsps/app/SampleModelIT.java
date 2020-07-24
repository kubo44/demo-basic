package com.whitestein.lsps.app;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.whitestein.lsps.test.LspsRemote;
import com.whitestein.lsps.test.ModelInstance;
import com.whitestein.lsps.test.Module;

/**
 * Example test which does not test via clicking on the UI - instead, it remotely calls functions and executes expressions,
 * and asserts on the results obtained from these calls. These kind of tests generally run way faster than the UI tests.
 */
public class SampleModelIT {

	/**
	 * This is the path to your model. The path must point to a directory containing the Eclipse .project descriptor file.
	 * This path is relative to the *-app-tester project.
	 * <p></p>
	 * The module being uploaded must be present in this directory.
	 */
	private static final File PATH_TO_MODEL = new File("src/modules").getAbsoluteFile();

	private static LspsRemote remote() {
		return LspsRemote.ADMIN;
	}

	@Before
	@SuppressWarnings("javadoc")
	public void prepare() {
		remote().unloadAllModules();
	}

	@After
	@SuppressWarnings("javadoc")
	public void cleanup() {
		remote().unloadAllModules();
	}

	/**
	 * Uploads module and creates new instance. Runs an expression in this instance context.
	 *
	 * @throws Exception
	 */
	@Test
	public void myTestNoUI() throws Exception {
		final Module module = remote().uploadModule("validationTest", "1.0", PATH_TO_MODEL);
		final ModelInstance modelInstance = module.startModelInstance();
		modelInstance.execute("findAll(Person)");
	}
}
