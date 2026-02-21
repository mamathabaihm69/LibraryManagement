package com.example.finaldb;

public class Book {
    public String isbn, title, author, category, edition, description, imageUrl;
    public int totalBooks, issuedBooks;

    public Book() {} // Required for Firebase

    public Book(String isbn, String title, String author, String category, String edition,
                int totalBooks, int issuedBooks, String description, String imageUrl) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.edition = edition;
        this.totalBooks = totalBooks;
        this.issuedBooks = issuedBooks;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}