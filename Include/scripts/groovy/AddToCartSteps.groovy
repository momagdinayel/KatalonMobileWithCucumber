import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.util.KeywordUtil;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.kms.katalon.core.testobject.TestObject; // Import the TestObject class

class AddToCardSteps {

    @Given("I have launched the General Store app")
    def appOpen() {
        // Construct the dynamic path for the APK file
        String appPath = RunConfiguration.getProjectDir() + '/app/General_Store.apk'

        // Launch the app
        Mobile.startApplication(appPath, false)

        // Log the action for confirmation
        KeywordUtil.logInfo("App Opened using path: " + appPath)
    }

    @And("Select Egypt country from the dropdown list")
    def selectCountry() {
        // Tap on the country select button
        TestObject selectCountryButton = findTestObject('Object Repository/selectCountry_Btn')
        Mobile.tap(selectCountryButton, 0)
        KeywordUtil.logInfo("Country select button tapped")

        // Scroll to find Egypt and tap on it
        TestObject egyptOption = findTestObject('Object Repository/selectEgypt')
        Mobile.scrollToText("Egypt", false, 5)
        Mobile.tap(egyptOption, 0)
        KeywordUtil.logInfo("Country selected: Egypt")
    }

    @And("Enter your name {string} in the text field")
    def enterName(String name) {
        // Enter name in the name field
        TestObject nameField = findTestObject('Object Repository/field_name')
        Mobile.setText(nameField, name, 0)
        KeywordUtil.logInfo("Name entered: "+name)
    }

    @And("Select Male gender")
    def selectMaleGender() {
        // Tap on the Male gender button
        TestObject maleButton = findTestObject('Object Repository/Male_B')
        Mobile.tap(maleButton, 0)
        KeywordUtil.logInfo("Male gender selected")
    }

    @And("Tap on the Let's Shop button")
    def clickLetsShopButton() {
        // Tap on the Let's Shop button
        TestObject shopButton = findTestObject('Object Repository/Shop_Btn')
        Mobile.tap(shopButton, 0)
        KeywordUtil.logInfo("Let's Shop button clicked")
    }

    @And("Add two products to the cart")
    def addPrudcts() {
		// Adding to products to the cart
        TestObject addFirstProduct = findTestObject('Object Repository/Add1stProductCart')
        Mobile.tap(addFirstProduct, 0)
        KeywordUtil.logInfo("First item Added")
        TestObject addSecondProduct = findTestObject('Object Repository/Add2ndProductCart')
        Mobile.tap(addSecondProduct, 0)
        KeywordUtil.logInfo("Second item Added")
    }

    @When("Navigate to the cart screen")
    def navigateToCartScreen() {
        // Navigating to the cart screen
        TestObject cartButton = findTestObject('Object Repository/cartButon')
        Mobile.tap(cartButton, 0)
        KeywordUtil.logInfo("Cart Opened")
        println("Navigated to cart screen")
    }

    @Then("Assert that products are displayed and the total amount equals the sum of them")
    def assertProductsAndTotal() {
        try {
			TestObject firstItemName = findTestObject('Object Repository/firstItemName')
			// Verify if the item is present
			boolean isFirstItemPresent = Mobile.verifyElementPresent(firstItemName, 2)
			// Assert the item presence
			assert isFirstItemPresent : "The first item is not displayed"
			
			TestObject secondItemName = findTestObject('Object Repository/secondItemName')
			// Verify if the item is present
			boolean isSecondItemPresent = Mobile.verifyElementPresent(secondItemName, 2) 
			// Assert the item presence
			assert isSecondItemPresent : "The second item is not displayed"
			
			KeywordUtil.logInfo("The item is displayed successfully")
            float firstProductPrice = getProductPrice(findTestObject('Object Repository/firstItemPrice'))
            float secondProductPrice = getProductPrice(findTestObject('Object Repository/secondItemPrice'))
            float totalPrice = getProductPrice(findTestObject('Object Repository/sumOfPrice'))

            assert firstProductPrice + secondProductPrice == totalPrice :
                "Total amount is incorrect. Expected: " + (firstProductPrice + secondProductPrice) + ", Found: " + totalPrice

            KeywordUtil.logInfo("Assertion passed: Total amount is correct")
            println("Assertion passed: Products are displayed, and total is correct")
        } catch (NumberFormatException e) {
            KeywordUtil.markFailed("Error converting price to float: " + e.message)
        } catch (Exception e) {
            KeywordUtil.markFailed("Error occurred while fetching or comparing prices: " + e.message)
        }
    }

    private float getProductPrice(TestObject productPriceObject) {
        String priceText = Mobile.getText(productPriceObject, 0).replace('$', '').trim();
        return Float.parseFloat(priceText);
    }
}