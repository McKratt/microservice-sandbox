Feature: Manage Addresses on a partner

  - A partner should have at least a main address
  - It can be only one main address
  - A partner can have many addresses


  Scenario: New Address
    Given an existing partner without address
    When I add an address to this partner
    Then the partner has exactly one address
    Then this address is the main address of the partner