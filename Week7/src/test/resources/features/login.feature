Feature: As a User, I want to be able to login to the Hacker News website

  Scenario: User navigates to the login page from the Homepage
    Given I am on the Hacker News Homepage
    When I click on the Login link
    Then I should be taken to the Login Page
    And  the login title will appear right above the login form