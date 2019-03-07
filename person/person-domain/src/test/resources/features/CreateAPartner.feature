Feature: Partner Creation

  - A partner should always has a name, a forename and a birth date
  - He can also has a Social Security Number
  - The system should provide a unique identifier for every partner
  - The name and the forename should not have more than 30 character to be conform to the postal recommendation in address
  - The birth date should be in the past

  Scenario: Creation without social security number
    When I create a partner with name "Do" and forename "John" born the 16.12.1981
    Then this new partner should have an id

#  Scenario: Creation with social security number
#    When I create a partner with the following data :
#      | Do | John | 16.12.1981 | 7561234567890 |
#    Then I should have a new partner stored in the system
#
#  Scenario Outline: Creation should not be authorized if something is missing
#    When I create a partner with name <name> and forename <forename> born the <day>.<month>.<year>
#    Then I should receive an error mentioning that the info <info> is missing
#    Examples:
#      | name | forename | day | month | year | info      |
#      |      | John     | 16  | 12    | 1981 | name      |
#      | Do   |          | 16  | 12    | 1981 | forename  |
#      | Do   | John     | 9   | 12    | 9999 | birthDate |