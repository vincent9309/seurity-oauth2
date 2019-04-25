<html>
<head>
</head>
<body>
<#if RequestParameters['error']??>
	<div>
		There was a problem logging in. Please try again.
	</div>
</#if>
	<div>
		<form role="form" action="login" method="post">
		  <div>
		    <label for="username">Username:</label>
		    <input type="text" id="username" name="username"/>
		  </div>
		  <div>
		    <label for="password">Password:</label>
		    <input type="password" id="password" name="password"/>
		  </div>
		  <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		  <button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>