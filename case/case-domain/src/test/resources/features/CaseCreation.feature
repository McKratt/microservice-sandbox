@thx
Feature: Case Creation

  To create a Case you should have a Partner.
  The Created Case should have an id auto assigned
  An Event notifying the creation of the cas should be emitted

  Scenario: Create Case with Partner
    When we create a case with a Partner number P12345678
    Then this Case should have an id
#    Then an Event mentioning the new case is emitted