<%@ page contentType="text/html;charset=UTF-8" session="false" import="com.whitestein.lsps.human.app.tools.ThemeManager"%><%
	//Redirect to the app if user is already authenticated
	if (request.getRemoteUser() != null) {
		response.sendRedirect("ui");
		return;
	}
%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['l-locale-cookie'].value}" />
<fmt:setBundle basename="com.whitestein.lsps.vaadin.webapp.localization" />
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	<title>Process Application</title>

  <!-- Below script allows theming of login page -->
	<script>
		function loadcssfile(rel, type, filename) {
			var fileref = document.createElement("link");
			fileref.setAttribute("rel", rel);
			fileref.setAttribute("type", type);
			fileref.setAttribute("href", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
		var storage = window.localStorage;
		var theme = "<%=ThemeManager.LSPS_DEFAULT_LOGIN_THEME%>";
		loadcssfile("icon", "image/vnd.microsoft.icon", "VAADIN/themes/" + theme + "/favicon.ico");
		loadcssfile("shortcut icon", "image/vnd.microsoft.icon", "VAADIN/themes/" + theme + "/favicon.ico");
		loadcssfile("stylesheet", "text/css", "VAADIN/themes/" + theme + "/styles.css"); //dynamically load and add this .css file

		window.addEventListener("load", function (event) {
			document.body.className = theme + " login-page";
			document.body.style.display = "block";
		});
	</script>

  <!-- Do not show the page until the above theme is loaded  -->
  <style>
		html, body {
			margin: 0;
			padding: 0;
			height: 100%;
		}

		body {
			display: none;
		}
	</style>
	
	<script src="classList.js"></script> <!-- IE8 compatibility -->
  
  <!-- -->
	<script>
		window.addEventListener("load", function (event) {
			document.addEventListener('keyup', function (event) {
				var code = event.keyCode || event.which;
				if (code === 13) {
            document.forms['loginform'].submit()
				}
			});
		});
	</script>

</head>

<body>
  <fmt:message key="login_username" var="username_placeholder" />
  <fmt:message key="login_password" var="password_placeholder" />
  <fmt:message key="login_failed"   var="login_failed"/>
  <% boolean requestContainsError = ((request.getParameterMap().containsKey("failed"))||((request.getQueryString() != null)&&(request.getQueryString().contains("failed"))));
     String errorStateClass = requestContainsError ? "error" : "";
     String inputFieldTitle = requestContainsError ? (String)pageContext.getAttribute("login_failed") : "";
  %>
  
	<!-- CONTENT START -->
	<div id="container-content">
		<form name="loginform" class="loginform <%=errorStateClass%>" action="j_security_check" method="post" >
			<div id="loginHeader"></div>
			<table>
				<tbody>
					<tr>
						<td class="field-row name" title="<%=inputFieldTitle%>">
							<input type="text" placeholder="${username_placeholder}" name="j_username" id="username" autofocus="autofocus" autocapitalize="off" />
						</td>
					</tr>
					<tr>
						<td class="field-row password" title="<%=inputFieldTitle%>">
						  <input type="password" placeholder="${password_placeholder}" name="j_password" id="password" />
						</td>
					</tr>
					<tr>
						<td>
							<div class="login-button" tabindex="0" onclick="document.forms['loginform'].submit()">
							  <fmt:message key="signup" />
							</div>
						</td>
					</tr>
				</tbody>
			</table>

		</form>
	</div>

	<!-- FOOTER -->
	<div id="footer">
		${project.parent.description} ${project.version} (<fmt:message key="basedOnLsps" /> ${lsps.version})
		<!-- Revision: ${buildNumber}, Built: ${app.buildTimestamp} --><br />
		&#0169; ${lsps.copyrightYears} Whitestein Technologies AG
	</div>

</body>

</html>