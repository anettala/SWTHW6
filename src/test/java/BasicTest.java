import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class BasicTest extends TestHelper {


    private String username = "testuser";
    private String password = "123456";

    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }


    /*
    In class Exercise

    Fill in loginLogoutTest() and login mehtod in TestHelper, so that the test passes correctly.

     */
 /*   @Test
    public void loginLogoutTest(){

        login(username, password);

        WebElement logout = driver.findElement(By.linkText("Logout"));
        assertNotNull(logout);

        logout();

        // assert that correct page appeared
        WebElement login = driver.findElement(By.linkText("Login"));
        assertNotNull(login);
    }
*/
    /*
    In class Exercise

     Write a test case, where you make sure, that one can’t log in with a false password

     */
/*    @Test
    public void loginFalsePassword() {
        String wrongpass = "wrongpassword";
        login(username, wrongpass);

        String expected = "Invalid user/password combination";
        String actual = driver.findElement(By.id("notice")).getText();

        assertEquals(expected, actual);
    }
*/
    //end-user test cases
    @Test
    public void addItemToCart() {
        //finds the product from the list of products and adds them to the cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();

        //finds the product from the cart
        WebElement B4_SG_inCart = driver.findElement(By.id("current_item"));
        String itemName = B4_SG_inCart.getText();

        //asserts that there is a product in the cart
        assertNotNull(B4_SG_inCart);
        //asserts that the correct product is in the cart
        assertEquals("1× B45593 Sunglasses €26.00 ↓ ↑ X", itemName);
    }

    @Test
    public void increaseQuantity() {
        //finds product and adds to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //finds the item in cart and increases quantity
        WebElement B4_SG_inCart = driver.findElement(By.id("current_item"));
        B4_SG_inCart.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[5]/a")).click();
        //asserts that quantity increased
        String itemName = driver.findElement(By.id("current_item")).getText();
        assertTrue(itemName.startsWith("2×"));
    }

    @Test
    public void decreaseQuantity() {
        //finds product and adds to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //finds the item in cart and decreases quantity
        WebElement B4_SG_inCart = driver.findElement(By.id("current_item"));
        B4_SG_inCart.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[4]/a")).click();
        //asserts that cart is empty since the quantity decreased from 1 to 0
        assertFalse(isElementPresent(By.id("current_item")));
    }

    @Test
    public void deleteItem() {
        //finds product and adds to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //finds the item in cart and deletes it
        WebElement B4_SG_inCart = driver.findElement(By.id("current_item"));
        B4_SG_inCart.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[6]/a")).click();
        //asserts that cart is empty because the only product is deleted
        assertFalse(isElementPresent(By.id("current_item")));
    }

    @Test
    public void deleteCart() {
        //adds two items to the cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        WebElement SG_2AR_Main = driver.findElement(By.id("Sunglasses 2AR_entry"));
        SG_2AR_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[4]/div[2]/form/input[1]")).click();
        //finds the empty cart button and clicks it
        WebElement emptyCart = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[1]/input[2]"));
        emptyCart.click();
        //asserts that the cart is empty (when cart is empty, no title is displayed) + notice on main page
        assertFalse(isElementPresent(By.id("cart_title")));
        assertEquals("Cart successfully deleted.", driver.findElement(By.id("notice")).getText());
    }

    @Test
    public void searchByName() {
        //finds the search input and inputs "book"
        driver.findElement(By.id("search_input")).sendKeys("book");
        //asserts that only the Web Application Testing Book is displayed
        assertTrue(driver.findElement(By.id("Web Application Testing Book_entry")).isDisplayed());
        assertFalse(driver.findElement(By.id("B45593 Sunglasses_entry")).isDisplayed());
        assertFalse(driver.findElement(By.id("Sunglasses 2AR_entry")).isDisplayed());
        assertFalse(driver.findElement(By.id("Sunglasses B45593_entry")).isDisplayed());
    }


    @Test
    public void filterSunglassesCategory() {
        //goes to the sunglasses category
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[2]/a")).click();
        //asserts that only products in the sunglasses category is on the page
        List<WebElement> products = driver.findElements(By.id("category"));
        for (WebElement category: products) {
            assertEquals("Category: Sunglasses", category.getText());
        }
    }

    @Test
    public void filterBooksCategory() {
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[3]/a")).click();

        List<WebElement> products = driver.findElements(By.id("category"));
        for (WebElement category: products) {
            assertEquals("Category: Books", category.getText());
        }
    }
    //expected failure
    @Test
    public void filterOtherCategory() {
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[4]/a")).click();

        List<WebElement> products = driver.findElements(By.id("category"));
        for (WebElement category: products) {
            assertEquals("Category: Other", category.getText());
        }
    }

    @Test
    public void purchaseCart() {
        //adds item to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //goes to checkout
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();
        //fills in details
        driver.findElement(By.id("order_name")).sendKeys("name");
        driver.findElement(By.id("order_address")).sendKeys("address");
        driver.findElement(By.id("order_email")).sendKeys("email");
        driver.findElement(By.id("order_pay_type")).sendKeys("Check");
        //clicks purchase button and asserts that order receipt appears/order went through
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        assertTrue(driver.findElement(By.id("order_receipt")).isDisplayed());
    }
    //expected failure
    @Test
    public void purchaseCartReceiptTotal() {
        //adds the same item two times (quantity 2x)
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        WebElement SG_add = B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        SG_add.click();
        SG_add.click();
        //clicks checkout button, fills in details and places order
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();
        driver.findElement(By.id("order_name")).sendKeys("name");
        driver.findElement(By.id("order_address")).sendKeys("address");
        driver.findElement(By.id("order_email")).sendKeys("email");
        driver.findElement(By.id("order_pay_type")).sendKeys("Check");
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        //asserts that the receipt shows the correct total of items (sunglasses are 26€, total should be 52€)
        assertEquals("€52.00", driver.findElement(By.className("total_cell")).getText());
    }
    //negative tests
    @Test
    public void searchByNameNonExistentProduct() {
        //finds the search input and inputs "item"
        driver.findElement(By.id("search_input")).sendKeys("item");
        //asserts that no products are displayed
        List<WebElement> entries = driver.findElements(By.className("entry"));
        for (WebElement entry : entries) {
            assertFalse(entry.isDisplayed());
        }
    }

    @Test
    public void checkoutNoDetailsFilled() {
        //adds product to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //goes to checkout and tries to place order
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        //collects error messages
        WebElement errorDiv = driver.findElement(By.id("error_explanation"));
        List<WebElement> errors = errorDiv.findElements(By.tagName("li"));
        String[] expectedErrors = {"Name can't be blank", "Address can't be blank", "Email can't be blank", "Pay type is not included in the list"};
        //asserts that expected errors show up
        for (int i = 0; i < errors.size(); i++) {
            assertEquals(expectedErrors[i], errors.get(i).getText());
        }
    }
    @Test
    public void checkoutNoNameFilled() {
        //adds product to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //goes to checkout, fills out address, email and pay type and tries to place order
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();

        driver.findElement(By.id("order_address")).sendKeys("address");
        driver.findElement(By.id("order_email")).sendKeys("email");
        driver.findElement(By.id("order_pay_type")).sendKeys("Check");
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        //asserts that no name notice shows up.
        String error = driver.findElement(By.id("error_explanation")).getText();
        assertEquals("Name can't be blank", error);
    }
    @Test
    public void checkoutNoAddressFilled() {
        //adds product to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //goes to checkout, fills out name, email and pay type and tries to place order
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();

        driver.findElement(By.id("order_name")).sendKeys("name");
        driver.findElement(By.id("order_email")).sendKeys("email");
        driver.findElement(By.id("order_pay_type")).sendKeys("Check");
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        //asserts that no address notice shows up
        String error = driver.findElement(By.id("error_explanation")).getText();
        assertEquals("Address can't be blank", error);
    }

    @Test
    public void checkoutNoEmailFilled() {
        //adds product to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //goes to checkout, fills out name, address and pay type and tries to place order
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();

        driver.findElement(By.id("order_name")).sendKeys("name");
        driver.findElement(By.id("order_address")).sendKeys("address");
        driver.findElement(By.id("order_pay_type")).sendKeys("Check");
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        //asserts that no email notice shows up
        String error = driver.findElement(By.id("error_explanation")).getText();
        assertEquals("Email can't be blank", error);
    }

    @Test
    public void checkoutNoPayTypeFilled() {
        //adds product to cart
        WebElement B4_SG_Main = driver.findElement(By.id("B45593 Sunglasses_entry"));
        B4_SG_Main.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]")).click();
        //goes to checkout, fills out name, address and email and tries to place order
        driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input")).click();

        driver.findElement(By.id("order_name")).sendKeys("name");
        driver.findElement(By.id("order_address")).sendKeys("address");
        driver.findElement(By.id("order_email")).sendKeys("email");
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input")).click();
        //asserts that no pay type notice shows up
        String error = driver.findElement(By.id("error_explanation")).getText();
        assertEquals("Pay type is not included in the list", error);
    }

}
