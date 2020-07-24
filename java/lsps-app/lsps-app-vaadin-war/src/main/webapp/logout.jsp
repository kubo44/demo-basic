<%
	session.invalidate();

	if (com.whitestein.lsps.common.JEEServer.getRuntime() == com.whitestein.lsps.common.JEEServer.WEBSPHERE) {
		response.sendRedirect("ibm_security_logout?logoutExitPage=ui");
	} else {
		response.sendRedirect("ui");
	}
%>
