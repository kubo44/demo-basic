package com.whitestein.lsps.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.whitestein.lsps.os.exception.PersonNotFoundException;
import com.whitestein.lsps.test.LspsRemote;
import com.whitestein.lsps.test.ModelInstance;
import com.whitestein.lsps.test.Module;
import com.whitestein.lsps.test.Person;
import com.whitestein.lsps.test.SetupUtils;
import com.whitestein.lsps.test.web.NamedScreenshotOnFailure;
import com.whitestein.lsps.test.web.UIDefaultAppTester;
import com.whitestein.lsps.test.web.components.LspsButton;
import com.whitestein.lsps.test.web.components.LspsTextBox;

/**
 * An example test which uploads a model and tests it. The model is form based. If you want to see example 
 * of ui based module, see {@link SampleUIIT}.
 * 
 * IMPORTANT: A Vaadin TestBench license is required to be present in order for the framework to work. If you do not plan 
 * to purchase TestBench license and still wish to test the UI, you will need to implement Selenium tests yourself.
 */
@SuppressWarnings("javadoc")
public class SampleFormsUIIT {

	/**
	 * This is the path to your model. The path must point to a directory containing the Eclipse .project descriptor file.
	 * This path is relative to the *-app-tester project.
	 * <p></p>
	 * The module being uploaded must be present in this directory.
	 */
	private static final File PATH_TO_MODEL = new File("src/modules").getAbsoluteFile();

	private static final String VAADIN_APP_CONTEXT_ROOT = "lsps-application";

	// initializes the helper class
	protected UIDefaultAppTester app = new UIDefaultAppTester(SetupUtils.createChromeDriver(), VAADIN_APP_CONTEXT_ROOT);

	/**
	 * This will make a screenshot of the page on test failure.
	 */
	@Rule
	public NamedScreenshotOnFailure screenshotOnFailureRule = new NamedScreenshotOnFailure(app, true);

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
	 * Log as two different users with two different roles and check whether they see what they should.
	 * 
	 * @throws PersonNotFoundException 
	 */
	@Test
	public void roleTest() throws PersonNotFoundException {
		/*
		 * Upload and run 'roleTest' module
		 */
		final Module module = remote().uploadModule("formRoleTest", "1.0", PATH_TO_MODEL);
		final ModelInstance modelInstance = module.startModelInstance();
		/*
		 * Initialize testing users
		 */
		Person administrator = remote().findOrCreatePerson("testAdministrator");
		administrator.setPassword("password");
		administrator.assignSecurityRoles("ProcessManager");
		administrator.addRole("formRoleTest::Administrator");

		Person editor = remote().findOrCreatePerson("testEditor");
		editor.setPassword("password");
		editor.assignSecurityRoles("ProcessManager");
		editor.addRole("formRoleTest::Editor");

		/*
		 * Login editor user and open Todo for creating content
		 */
		app.loginAndWait("testEditor", "password");
		app.openTodo("Create content");
		/*
		 * Input and submit content, this forwards the process to the next step
		 */
		LspsTextBox content = app.findByLspsId("INPUT").as().textBox();
		assertEquals("", content.getValue());
		content.setValue("Lorem ipsum");
		LspsButton submit = app.findByLspsId("SUBMIT").as().button();
		submit.click();
		/*
		 * Test if submit went through correctly
		 */
		assertTrue(app.isCurrentPageTodoList());
		/*
		 * Test if current editor user sees any Todos, he should not because his Todo was submitted and he has no rights to revise content
		 */
		assertTrue(modelInstance.findAliveTodosForPerson(editor).isEmpty());
		/*
		 * Logout editor user and login administrator user to test submitting of revised content
		 */
		app.logout();
		app.loginAndWait("testAdministrator", "password");
		app.openTodo("Revision of content");
		/*
		 * Test if the value is the same as inputted by editor
		 */
		content = app.findByLspsId("INPUT").as().textBox();
		assertEquals("Lorem ipsum", content.getValue());
		/*
		 * Submit revised content
		 */
		submit = app.findByLspsId("SUBMIT").as().button();
		submit.click();
		/*
		 * Test correct submission
		 */
		assertTrue(app.isCurrentPageTodoList());
	}

	/**
	 * Open validation task and test its fields and buttons.
	 */
	@Test
	public void validationTest() {
		// Login admin:admin and upload testing module
		app.loginAdminAdminAndWait();
		remote().uploadModule("formValidationTest", "1.0", PATH_TO_MODEL);

		// Run model and open testing task
		app.runModel("formValidationTest");
		app.openTodo("Validation task");

		// Find input, test if it is empty (it should)
		LspsTextBox number = app.findByLspsId("INPUT").as().textBox();
		assertEquals("", number.getValue()); // no number is entered upon todo start

		// Set incorrect value, find submit button and try to submit the form
		number.setValue("Jane Doe");
		final LspsButton submit = app.findByLspsId("SUBMIT").as().button();
		submit.click();

		/*
		 * We need to find the element on page again to avoid selenium stale element exception. The submit might have 
		 * cause the input field to be deleted element and replaced it new field with the same ID and attributes. Although 
		 * the replacement element looks identical it is different; the driver has no way to determine that the replacements 
		 * are actually what's expected. 
		 */
		//number = app.findByLspsId("INPUT").as().textBox();

		// Test if error message is shown (it should)
		assertFalse(number.isValid());

		//Enter correct value and re-test submission
		number.setValue("123");
		submit.click();
		assertTrue(app.isCurrentPageTodoList());
	}
}
