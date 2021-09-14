Feature: TopUp Account
  This feature describes various scenarios for users adding funds to their revolut account(s)

  #As a user, I can topup my Revolut account using my debit card

  Scenario: Add money to Revolut account using debit card
    Given Danny has 10 euro in his euro Revolut account
    And Danny selects 100 euro as the topUp amount
    And  Danny selects his DebitCard as his topUp method
    #And  Danny selects his BankAccount as his topUp method
    When Danny tops up
    Then The new balance of his euro account should now be 110


  Scenario: Add money to Revolut account using bank account
    Given Danny has 20 euro in his euro Revolut account
    And Danny selects 230 euro as the topUp amount
    And  Danny selects his BankAccount as his topUp method
    #And  Danny selects his BankAccount as his topUp method
    When Danny tops up
    Then The new balance of his euro account should now be 250



  #ToDo implement the remaining scenarios listed below

  #To implement this scenario you will need to use data tables
    # https://cucumber.io/docs/cucumber/api/
  Scenario Outline: Add various amounts to Revolut account
    Given Danny has a starting balance of <startBalance>
    And Danny selects his DebitCard as his topUp method
    When Danny now tops up by <topUpAmount>
    Then The balance in his euro account should be <newBalance>
    Examples:
      | startBalance| topUpAmount | newBalance  |
      | 0           | 100         | 100         |
      | 14          | 20          | 34          |
      | 23          | 30          | 53          |

  Rule: The account balance shouldn't change if the topup payment request is rejected by the payment service

    #The scenarios below will need a payment service that accepts or rejects a request to add funds
    Scenario: Payment service rejects the request
      Given Danny has a starting balance of 45.0
      And Danny selects his DebitCard as his topUp method
      And Danny has 0.0 euro in this DebitCard
      When Danny now tops up by 50.0
      Then The new balance of his euro account should now be 45.0

    Scenario: Payment service rejects the request
      Given Danny has a starting balance of 45.0
      And Danny selects his DebitCard as his topUp method
      And Danny has 0.0 euro in this DebitCard
      When Danny now tops up by 50.0
      Then The transaction should be returned as false

    Scenario: Payment service accepts the request
      Given Danny has a starting balance of 50.0
      And Danny selects his DebitCard as his topUp method
      And Danny has 100.0 euro in this DebitCard
      When Danny now tops up by 50.0
      Then The transaction should be returned as true

  Rule: If money is sent to a friends account it should be removed from the users account and added to the friends account.

    Scenario: Money is added to a friends account.
      Given a person named Durgesh
      And Danny has a starting balance of 20.0
      And Durgesh has a starting balance of 20.0
      When Danny sends Durgesh 20.0 euros
      Then The balance in Durgesh euro account should be 40.0

    Scenario: Money is removed from senders account.
      Given a person named Durgesh
      And Danny's starting balance is 40.0
      When Danny sends Durgesh 20.0 euros
      Then The balance in Durgesh euro account should be 20.0

  Rule: If friends split a bill. An equal amount of money is removed from each account.

    Scenario: Bills can be split between users
      Given a person named Durgesh
      And Danny has a starting balance of 20.0
      And Durgesh has a starting balance of 20.0
      When A bill of 30.0 euros is Split
      Then The new balance of his euro account should now be 5.0