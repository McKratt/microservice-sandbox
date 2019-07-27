Feature: Person Creation

  - A person should always has   name, a forename, a birth date and a main address
  - He can also has a Social Security Number but it is not mandatory
  - The system should provide a unique identifier for every person
  - The name and the forename should not have more than 30 character to be conform to the postal recommendation in address
  - The birth date should be in the past

  Scenario: Creation without social security number
    When I create a valid person
    Then this new person should have an id

  Scenario: Creation with social security number
    When I create a valid person with a social number
    Then this new person should have an id

  Scenario Outline: Creation should not be authorized if something is missing
    When I create a person with name "<name>" and forename "<forename>" born the <day>.<month>.<year> and located at "<address>"
    Then I should receive an error mentioning that the mandatory field "<field>" is missing

    Examples:
      | name | forename | day | month | year | address      | field     |
      |      | John     | 16  | 12    | 1981 | "My Address" | name      |
      | Do   |          | 16  | 12    | 1981 | "My Address" | forename  |
      | Do   | John     | 9   | 12    | 9999 | "My Address" | birthDate |
      | Do   | John     | 16  | 12    | 1981 |              | address   |