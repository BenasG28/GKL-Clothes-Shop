Feature: User Login

  Scenario: Valid Login
    Given the user is on the login screen
    When the user enters valid credentials and clicks login
    Then the main application screen should be displayed

  Scenario: Invalid Login
    Given the user is on the login screen
    When the user enters invalid credentials and clicks login
    Then an alert should be shown
