Feature: As a User, I want to be able to navigate the Comments Page

  Scenario: User navigates to the comments page from the Homepage
    Given I am on the Hacker News Homepage
    When I click on the comments link
    Then I should be taken to the Comments Page
    And the page title will includes comments