package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
	public static void main(String[] args) throws IOException {
		User user = null;

		List<Book> bookDatabase = initializeBookDatabase();

		boolean loopActive = true;
		while (loopActive) {
			if (user == null) {
				try {
					// Attempt to create a new user
					user = CreateNewUser();
				} catch (Exception e) {
					System.out.println("Error creating your account! Let's try again.'");
				}
			} else {
				// If user already exists
				displayOptions(user);

				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String response = reader.readLine();

				switch (response) {
					case "1":
						// Search book
						searchBook(bookDatabase);
						break;
					case "2":
						// Borrow book
						borrowBook(user, bookDatabase);
						break;
					case "3":
						// Return book
						returnBook(user, bookDatabase);
						break;
					case "4":
						// Edit profile
						System.out.println("Let's edit your profile, bro");
						break;
					case "5":
						// Reset password
						user.resetPassword();
						break;
					case "6":
						// Exit
						loopActive = false;
						System.out.println("Exiting...");
						break;
					default:
						System.out.println("You selected an invalid option");
						break;
				}
			}
		}
	}

	// Collects user data / onboarding
	public static User CreateNewUser() throws IOException {
		System.out.println("Welcome to The Java Book Store");
		System.out.println("Let's start by creating an account for you");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Set first name
		System.out.println("Enter your first name");
		String firstName;
		while (true) {
			firstName = reader.readLine();
			if (firstName != null && !firstName.isEmpty()) {
				break;
			} else {
				System.out.println("This field cannot be empty");
			}
		}

		// Set last name
		System.out.println("Enter your last name");
		String lastName;
		while (true) {
			lastName = reader.readLine();
			if (lastName != null && !lastName.isEmpty()) {
				break;
			} else {
				System.out.println("This field cannot be empty");
			}
		}

		// Set email
		System.out.println("Enter your email address");
		String email;
		while (true) {
			email = reader.readLine();
			if (email != null && !email.isEmpty()) {
				break;
			} else {
				System.out.println("This field cannot be empty");
			}
		}

		// Set password
		System.out.println("Enter a secured password (8 characters at least)");
		String password;
		while (true) {
			password = reader.readLine();
			if (password != null && password.length() >= 8) {
				System.out.println("Password set successfully");
				break;
			} else {
				System.out.println("You must enter at least 8 characters to set password");
			}
		}

		// Instance of user => Pass in user info as parameters
		return new User(firstName, lastName, email, password);
	}

	// Menu items method
	public static void displayOptions(User user) {
		System.out.println("userID: " + user.getUserID());
		System.out.println("MENU:");
		System.out.println("1. Search Book");
		System.out.println("2. Borrow Book");
		System.out.println("3. Return Book");
		System.out.println("4. Edit Profile");
		System.out.println("5. Reset Password");
		System.out.println("6. Exit");
	}

	// Book database method
	public static List<Book> initializeBookDatabase() {
		List<Book> books = new ArrayList<>();
		Gson gson = new Gson();
		InputStream inputStream = Main.class.getResourceAsStream("/booksLibrary.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		Type bookMapType = new TypeToken<Map<String, List<Book>>>() {
		}.getType();
		Map<String, List<Book>> bookMap = gson.fromJson(br, bookMapType);

		for (List<Book> bookList : bookMap.values()) {
			books.addAll(bookList);
		}
		return books;
	}

	// Search book method
	public static void searchBook(List<Book> bookDatabase) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the title or author of the book you are looking for:");
		String query = reader.readLine().toLowerCase();

		System.out.println("Search Results:");
		for (Book book : bookDatabase) {
			if (book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query)) {
				System.out.println(book);
			}
		}
	}

	// Borrow book
	public static void borrowBook(User user, List<Book> bookDatabase) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the title or author of the book you are looking for:");
		String query = reader.readLine().toLowerCase();

		for (Book book : bookDatabase) {
			if (book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query) && book.getQuantity() > 0) {
				user.borrowBook(book);
				book.decreaseQuantity();
				System.out.println("You have borrowed " + book.getTitle());
				return;
			}
		}
		System.out.println("Book not found or not available");
	}

	// Return book
	public static void returnBook(User user, List<Book> bookDatabase) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the title or author of the book you want to return");
		String query = reader.readLine().toLowerCase();

		for (Book book : user.getBorrowedBooks()) {
			if (book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query)) {
				user.returnBook(book);
				for (Book dbBook : bookDatabase) {
					if (dbBook.equals(book)) {
						dbBook.increaseQuantity();
						break;
					}
					System.out.println("You have returned " + book.getTitle());
					return;
				}
			}
		}
		System.out.println("Book not found in your borrowed list");
	}
}
