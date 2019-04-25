<html>
<head>
</head>
<body>
	<div>
		<h2>请确认是否授权</h2>

		<p>
			Do you authorize "${authorizationRequest.clientId}" at "${authorizationRequest.redirectUri}" to access your protected resources
			with scope ${authorizationRequest.scope?join(", ")}.
		</p>
        <form id="confirmationForm" name="confirmationForm"
			action="../oauth/authorize" method="post">
			<div>
				写
				<input name="scope.write" value="true" type="radio" />
				<input name="scope.write" value="false" type="radio" />
			</div>


			<div>
				读
				<input name="scope.read" value="true" type="radio" />
				<input name="scope.read" value="false" type="radio" />
			</div>
            <input name="user_oauth_approval" value="true" type="hidden" />
            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button class="btn btn-primary" type="submit">同意</button>
		</form>
		<form id="denyForm" name="confirmationForm"
			action="../oauth/authorize" method="post">
			<input name="user_oauth_approval" value="false" type="hidden" />
			<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button class="btn btn-primary" type="submit">拒绝</button>
		</form>
	</div>
</body>
</html>