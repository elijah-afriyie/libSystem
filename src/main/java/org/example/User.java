package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {
	// Private fields
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userID;
	private List<Book> borrowedBooks;

	// Constructor
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.borrowedBooks = new ArrayList<>();
	}

	// Getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String getUserID() {
		return userID;
	}

	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	// Borrow book Method
	public void borrowBook(Book book) {
		borrowedBooks.add(book);
	}

	// Return book method
	public void returnBook(Book book) {
		borrowedBooks.remove(book);
	}

	// Generate userID
	public String generateUserID() {
		String prefix = "SB-AA";
		String suffix = "-KN";
		Random random = new Random();
		StringBuilder sBuildr = new StringBuilder(prefix);

		for (int i = 0; i < 5; i++) {
			int digit = random.nextInt(10);
			sBuildr.append(digit);
		}

		sBuildr.append(suffix);

		// Restrict creating multiple instances of userID
		if (userID == null) {
			this.userID = sBuildr.toString();
		}
		return this.userID;
	}

	// Reset password method
	public void resetPassword() throws IOException {
		System.out.println("Enter old password");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String oldPassword = reader.readLine();
		if (oldPassword.equals(getPassword())) {
			System.out.println("Old password confirmed");

			System.out.println("Enter new password");
			String newPassword = reader.readLine();
			if (newPassword.length() >= 8) {
				this.password = newPassword;
				System.out.println("Password reset successfully");
			} else {
				System.out.println("There was an error resetting your password. Enter at lease 8 characters to proceed.");
			}
		} else {
			System.out.println("Password mismatch. Please try again");
		}
	}
}
