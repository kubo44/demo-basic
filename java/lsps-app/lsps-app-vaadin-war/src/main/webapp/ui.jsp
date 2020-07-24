<!DOCTYPE html>
<html>
<head>
	<script>
	  var parametersStr = window.location.search.substr(1);
    
    result = {};
    parametersStr.split("&").forEach(function(part) {
      var item = part.split("=");
      console.log(item[0]);
      console.log(item[1]);
      result[item[0]] = decodeURIComponent(item[1]);
    });

    var redirectTo = result['redirectTo'];
		if (redirectTo == null) {
      //just change location
      window.location = 'ui'
		} else {
		  window.location = decodeURIComponent((redirectTo + '').replace(/\+/g, ' '));
		}
	</script>
	<meta http-equiv="refresh" content="2;URL='ui'" />
</head>
</html>
