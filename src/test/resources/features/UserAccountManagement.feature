Feature: User Account Management

  Scenario: User wants to edit account information
    Given the user is logged in
    When the user navigates to the account settings page
    Then the system should display clear instructions on how to edit account information with text
    And the user should see input fields
    When the user edits their account information
    And confirms the changes
    Then the system should save the changes and update the user's account

