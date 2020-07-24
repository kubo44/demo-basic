<%@ page contentType="text/html;charset=UTF-8" session="false" import="com.whitestein.lsps.human.app.tools.ThemeManager"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['l-locale-cookie'].value}"/>
<fmt:setBundle basename="com.whitestein.lsps.vaadin.webapp.localization"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	<title>Process Application</title>

	<script>
		function loadcssfile(rel, type, filename){
			var fileref=document.createElement("link");
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
		
		window.onload = function(event) {
			document.body.className = theme + " login-page v-generated-body";
			document.body.style.display = "block";
		};
	</script>
	<style>
		html, body {
			margin: 0;
			padding: 0;
			height: 100%;
		}
		body{
			display: none;
		}
	</style>
	<script src="classList.js"></script> <!-- IE8 compatibility -->
</head>
 
<body>

<!-- Vaadin-Refresh -->
<div class="v-app">
<div class="v-ui fixed">
    
<!-- CONTENT START --> 
<div id="container-content" style="position: absolute; top: 40%; left: 0; margin-top: -50px; width: 100%">
<form name="loginform" class="open" action="j_security_check" method="post" style="margin: 0 auto; width: 310px">
<div id="loginHeader" style="display:none;"></div>
<table class="v-formlayout-margin-top v-formlayout-margin-bottom v-formlayout-spacing">
<tbody>
<tr class="v-formlayout-row">
	<td class="login-failed-caption" width="100%">
		<fmt:message key="login_failed"/>
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

</div>
</div>
</body>
</html>
