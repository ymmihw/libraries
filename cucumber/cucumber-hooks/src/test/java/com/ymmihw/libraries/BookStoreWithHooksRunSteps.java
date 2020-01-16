package com.ymmihw.libraries;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookStoreWithHooksRunSteps {

  private BookStore store;
  private List<Book> foundBooks;

  public BookStoreWithHooksRunSteps() {
    store = new BookStore();
    foundBooks = new ArrayList<>();
  }

  @Given("^The following books are available in the store$")
  public void haveBooksInTheStore(DataTable table) {
    List<List<String>> rows = table.asLists(String.class);

    for (List<String> columns : rows) {
      store.addBook(new Book(columns.get(0), columns.get(1)));
    }
  }

  @When("^I ask for a book by the author (.+)$")
  public void searchForBooksByAuthor(String author) {
    foundBooks = store.booksByAuthor(author);
  }

  @Then("^The salesperson says that there are (\\d+) books$")
  public void findBooks(int count) {
    assertEquals(count, foundBooks.size());
  }

  // ****************************

}
