import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

/** CheckStatus.java -
 *
 *  The following CheckStatus.java test script initializes a WebDriver that opens
 *  the desired web URL that holds the result of the textbook that required to be
 *  parsed. The page then timeouts for 5 seconds to ensure that the page loads
 *  successfully. Then using WebElement, the corresponding div class is checked
 *  whether it holds the appropriate Available or Checked out status. If it is
 *  Available, then the WebDriver remains open for the user to interact. Else it
 *  closes and finishes the test such that it is able to run at a later point.
 *
 *  To develop your own implementation, refer to [Ref-IM].
 *
 *  @author  Asad Mansoor
 *  @version 1.0, 12/26/2016
 *  @since   1.0
 * */



public class CheckStatus {
    WebDriver webDriver;
    WebElement webElement;
    boolean bookAvailable = false;
    final int TIMEOUT_SHORT = 5000; // 5 seconds delay
    final int TIMEOUT_LONG = 10000; // 10 seconds delay
    final String Book_URL = "http://catalogue.mcmaster.ca/catalogue/Search/Results?lookfor=Concurrency%3A++State%2C+Models%2C+%26+Java+Programs&type=AllFields&submit=FIND";



    /** Before Test -
     *  Set up the test by initializing the WebDriver and opening desired URL.
     *  After the browser open, initiate a timeout to ensure page loads successfully.
     *  */

    @Before
    public void initTestSet() {
        webDriver = new ChromeDriver();
        webDriver.get(Book_URL); // open desired url
        webDriverTimeout();
    }



    /** Test: getBookStatus -
     *  Use the WebDriver to find the element which holds the status of the textbook.
     *  For this particular system, the list of books are structured in a table, so
     *  I will only be parsing for the specific row such that the desired textbook holds.
     *  For each row, multiple parameters are given, but we are only interested in the last
     *  element which holds either its Available or Checked out.
     *
     *  If we are prompted with an Available status, we will assert the test result as true
     *  and keep the driver open for user interaction. Else, we would close the driver such
     *  that it can be executed at a later point.
     *
     *  Sample input of the web element provided below the code. See Comment [Ref_SI].
     *  */

    @org.junit.Test
    public void getBookStatus(){
        webElement = webDriver.findElement(By.cssSelector("*[class^='callnumAndLocation']")); // textbook information stored under this class
        List<WebElement> tableRows = webElement.findElements(By.tagName("tr"));

        String tableCheck = getElemText(tableRows); // results the desired parameters of a single textbook item
        String tableArray[] = tableCheck.split(" "); // within those parameters, the last element holds the status value
        int N = tableArray.length;

        if ((tableArray[N-1].contains("Available"))){
            bookAvailable = true;
        } else {
            webDriver.quit();
        }

        Assert.assertTrue("Book Available: " + String.valueOf(bookAvailable), bookAvailable);
    }

    /**[Ref_SI] - Sample Input of Web Element:
     * <div class="callnumAndLocation">
     *  <table class="table table-condensed">
     *      <tbody>
     *          <tr>
     *              <th>Location</th>
     *              <th>Call Number</th>
     *              <th>Item Type</th>
     *              <th>Status</th>
     *          </tr>
     *          <tr>
     *              <td> THODE LIBRARY - Thode Bookstacks </td>
     *              <td>QA 76.642 .M34 1999</td>
     *              <td>Book </td>
     *              <td>
     *                  <span class="text-success">Available</span>
     *              </td>
     *          </tr>
     *          <tr>
     *              <td> THODE LIBRARY - Thode Bookstacks </td>
     *              <td>QA 76.642 .M34 1999 CD-ROM</td>
     *              <td>Data disc </td>
     *              <td>
     *                  <span class="text-success">Available</span>
     *              </td>
     *          </tr>
     *      </tbody>
     *  </table>
     * </div>
     * */



    /** Method: webDriverTimeout -
     *  To ensure that web page is loaded before parsing web elements
     *
     *  Ideally instead of a sleep command, a method should be initiated such
     *  to return as soon as the web pages return at any variable of time. The
     *  command below may work, but it is not offered in the ChromeDriver:
     *
     *  webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
     *
     *  @exception InterruptedException On interrupted thread sleep.
     *  @see InterruptedException
     *  */

    private void webDriverTimeout(){
        try {
            Thread.sleep(TIMEOUT_SHORT);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    /** Method: getElemText -
     *  Getting the textbook parameters that is positioned at the top. If the
     *  page is not loaded, the list will return as empty and an
     *  IndexOutofBoundsException will be triggered. In such a case, increase
     *  the timeout delay duration (TIMEOUT_SHORT, TIMEOUT_LONG).
     *
     *  @exception IndexOutOfBoundsException On getting 2nd position index on empty list.
     *  @see IndexOutOfBoundsException
     *  */

    private String getElemText(List<WebElement> webElem){
        String tableParam = "";
        try{
            tableParam = webElem.get(1).getText();
        } catch (IndexOutOfBoundsException e){
            webDriver.quit();
            Assert.fail("Please select a longer delay duration.");
        }

        return tableParam;
    }
}


/**[Ref_IM] - Own Implementation
 *
 * When designing your own implementation, keep the following items in mind:
 * 1) Web URL - Ensure the link you are trying to automate can be reached with
 *              a single query. For example, the link noted above, can be accessed
 *              to display the result for that particular query search. For some
 *              system, retrieving the results may require multiple sessions/steps.
 *              In that case, you would need to automate each step of the search.
 *              This includes auto-filling the field text, pressing the button and
 *              checking the progress of each step being executed sequentially.
 *              Please refer to the documentation for more information.
 *
 *              @see <a href="http://www.seleniumhq.org/docs/">Selenium Docs</a>
 *
 * 2) Web Driver - You can initialize the default WebDriver, or associate different
 *                 browser such as (FireFox, Safari) to conduct your test. You will
 *                 find the chromedriver executable within this repo.
 *
 * 3) Page Timeout - A timeout will be required to ensure that the web page is load
 *                   successfully. Ideally the perferred method is indicated above.
 *                   Unfortunately, the method does not work well with the
 *                   chromedriver, so a simple wait is implemented for this app.
 *
 * 4) Parsing Content - This part will be different for each system use. Depending on
 *                      your requirements, you may want to utilize which content to
 *                      retrieve to make a judgement. For our system, we carefully
 *                      determined in which div class, the corresponding value was
 *                      embedded. In our computation, the data was stored in
 *                      a tabular format, hence structured in a way to get the parameter
 *                      of the first textbook element of the search list.
 *
 * 5) Asserting the Test Case - The Selenium test case verifies whether a test case
 *                              has passed or failed. Depending on the result, the
 *                              following shell script can determine on what to do
 *                              next depending on the specified requirements.
 *
 * */