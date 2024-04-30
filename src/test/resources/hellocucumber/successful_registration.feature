Feature: User can successfully register into the system

  User wants to register using appropriate data

  Scenario: User successfully registers
    Given the user opens the registration page
    When the user enters their username as "testUser"
    And the user enters their email address as "email@gmail.com"
    And enters their password as "Password123!"
    And repeats the password as "Password123!"
    And the user enters their home address as "Home street 5"
    And the user enters their phone number as "+0642932193"
    And the user enters their name as "Dominic"
    And the user enters their surname as "cool"
    And clicks the register button

  Scenario: User unsuccessfully registers
    Given the user opens the registration page
    When the user enters their username as ""
    And the user enters their email address as ""
    And enters their password as ""
    And repeats the password as ""
    And the user enters their home address as ""
    And the user enters their phone number as ""
    And the user enters their name as ""
    And the user enters their surname as ""
    And clicks the register button
