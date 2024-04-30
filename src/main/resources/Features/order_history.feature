Feature: View Order History
  As a user
  I want to see my order history
  So that I can track my past purchases

  Scenario: User views their order history
    Given the user is logged in
    When the user navigates to the order history tab
    Then the user should see a list of their past orders
