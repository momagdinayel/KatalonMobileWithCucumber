Feature: Add products to the cart

  Scenario: Add products and verify the total amount
    Given I have launched the General Store app
    And Select Egypt country from the dropdown list
    And Enter your name "Nayel" in the text field
    And Select Male gender
    And Tap on the Let's Shop button
    And Add two products to the cart
    When Navigate to the cart screen
    Then Assert that products are displayed and the total amount equals the sum of them