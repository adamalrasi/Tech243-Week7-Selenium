Feature: As a User, I want to be able to navigate the Hacker News website

  Scenario: User navigates to the past page from the Homepage
    Given I am on the Hacker News Homepage
    When I click on the Past link
    Then I should be taken to the Past Page
    And the page title will include yesterday's date

  Scenario: Navigating to the search page using the search text box
    Given I am on the Hacker News Homepage
    When I enter "java" into the Search text box
    Then I will go to the search page with the url parameter "java"
