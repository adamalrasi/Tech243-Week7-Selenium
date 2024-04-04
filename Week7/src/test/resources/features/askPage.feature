Feature: As a User, I want to be able to navigate to the ask page

  Scenario: User navigates to the ask page from the homepage
    Given I am on the Hacker News Homepage
    When I click on the ask link
    Then I should be taken to the ask page
    And the page title will include ask