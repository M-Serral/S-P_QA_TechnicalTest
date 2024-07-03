
Feature: SportsTab

  Background:
    Given I access to the demoQA page

    Scenario Outline: Check that results contain the input values
      When I fill input fields with these values "<Full Name>", "<Email>", "<Current Address>", "<Permanent Address>"
      And  I click in Submit button
      Then results match the input data
      Examples:
        | Full Name | Email                    |  Current Address                | Permanent Address           |
        | test user | test@blabla.com          | C. Dobla, 5, 28054 Madrid, Spain|Street X, 28013 Madrid, Spain|
        | John Smith| john.smith@mailinator.com| Street Smith 3, London, UK      |Street Smith 6, London, UK   |

    Scenario: Check that if you write an invalid email, after pressing Submit button, the email input has some error indicator
      When I fill Email input with this value "thisisnotanemail"
      And  I click in Submit button
      Then Email input has some error indicator

  Scenario: Check that if you write an invalid email, after pressing Submit button, there is no output produced
    When I fill Email input with this value "thisisnotanemail"
    And  I click in Submit button
    Then I check that invalid email is not shown

  Scenario: Check that if you write an invalid email, after writing a valid email, after pressing Submit button, the invalid email is not shown
    When I fill Email input with this value "thisisanemail@gmail.com"
    And  I click in Submit button
    And  I delete Email input
    And  I fill Email input with this value "thisisnotanemail"
    And  I click in Submit button
    Then I check that the output email has not changed




