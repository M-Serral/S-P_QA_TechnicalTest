
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

    Scenario: Check that after pressing Submit button, the email input has some error indicator and the fact that there is no output produced
      When I fill Email input with "thisisnotanemail"
      Then Email input has some error indicator
      And  I check that there is no output produced




