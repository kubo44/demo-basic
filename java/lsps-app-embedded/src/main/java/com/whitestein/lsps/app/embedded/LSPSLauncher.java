package com.whitestein.lsps.app.embedded;

import com.whitestein.lsps.launcher.Launcher;

/**
 * A launcher of LSPS application.
 */
public class LSPSLauncher extends Launcher {

	static {
		// do this before the JUL logger is initialized.
		System.setProperty("java.util.logging.config.file", "logging.properties");
		//if there is error during the server start, stop starting and exit
		System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
	}

	private static final int PORT = 8080;

	private static final String VAADIN_APP_CONTEXT_ROOT = "/lsps-application";

	/**
	 * The main entry point of LSPS launcher.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Launcher launcher = new LSPSLauncher();
		launcher.launch();
	}

	/**
	 * Create LSPS launcher.
	 *
	 * @throws Exception
	 */
	public LSPSLauncher() throws Exception {
		super(PORT);
	}

	@Override
	protected void setupApplications() throws Exception {
		// Vaadin application
		setupApplication(VAADIN_APP_CONTEXT_ROOT, "../lsps-app-vaadin-war/src/main/webapp");

		//web applications for process exposed web service interfaces
		setupApplication("/lsps-ws", "target/dependency/lsps-engine-ws.war");
		setupApplication("/lsps-os-ws", "target/dependency/lsps-os-ws.war");
		setupApplication("/lsps-human-ws", "target/dependency/lsps-human-ws.war");
		setupApplication("/lsps-monitoring-ws", "target/dependency/lsps-monitoring-ws.war");

		// Web management console
		setupApplication("/lsps-management", "target/dependency/lsps-webconsole-vaadin-war.war");
	}
}
