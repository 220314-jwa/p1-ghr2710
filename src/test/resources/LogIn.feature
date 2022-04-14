Feature: user login

	# you can use this when all of your scenarios have a certain prerequisite
	# Background: the user is not logged in

	Scenario: successful login
		Given the user is on the login page
		When the user enters the correct username
		And the user enters the correct password
		And the user clicks the login button
		Then the page will redirect
		
	Scenario: username does not exist
		Given the user is on the login page
		When the user enters an incorrect username
		And the user clicks the login button
		Then an incorrect credentials message will be displayed
		
	Scenario: incorrect password
		Given the user is on the login page
		When the user enters the correct username
		And the user enters the incorrect password
		And the user clicks the login button
		Then an incorrect credentials message will be displayed
		