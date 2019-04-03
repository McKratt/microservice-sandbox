Feature: Modify a partner

  You can modify all the field except the id.
  - The mandatory fields (name, forename, birth date) should no be put to an empty value nor to null.
  - Each time a Partner is modified an event is raised.
  - No event raised if the new values are the same to the old one.

#  Background: Exist a Partner
#    Given a Partner with named "George" "Einstein", born at 14.03.1879 and number 756947584363524

  Scenario: change the forename
    Given a Partner with named "George" "Einstein", born at 14.03.1879 and number 756947584363524
    When I change the forename of the partner to "Albert"
    Then the partner is now "Albert" "Einstein" born at 14.03.1879
    Then a modified partner event is emitted
