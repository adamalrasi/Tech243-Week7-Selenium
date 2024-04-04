Feature: As a User, I want to be able to navigate the Hacker News website

  Scenario: User navigates to the past page from the Homepage
    Given I am on the Hacker News Homepage
    When I click on the Past link
    Then I should be taken to the Past Page