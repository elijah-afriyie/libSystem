package org.example;

public class Book {
	// Private fields
	private String title;
	private String author;
	private String isbn;
	private Integer quantity;

	// constructor
	public Book(String title, String author, String isbn, Integer quantity) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.quantity = quantity;
	}

	// Getters
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

	public Integer getQuantity() {
		return quantity;
	}

	// Decrease quantity
	public void decreaseQuantity() {
		if (quantity > 0) {
			quantity--;
		}
	}

	// Increase quantity
	public void increaseQuantity() {
		quantity++;
	}

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", author='" + author + '\'' +
				", isbn='" + isbn + '\'' +
				", quantity=" + quantity +
				'}';
	}
}
