import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
// these lines import the necessary Java classes.


public class Library {
    // these lines define the properties and constructor of the Library class.
// the Library class has an attribute named "books" of type ArrayList.
// the constructor is automatically called when a Library object is created, and it loads Book objects from the "books.txt" file to populate the books attribute of the instantiated library object. If the file does not exist, a new file is created.
    private final ArrayList<Book> books;

    public Library(String name, int establishmentYear) {
        books = new ArrayList<>();
        try {
            File file = new File("books.txt");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                books.addAll((ArrayList<Book>) ois.readObject());
                ois.close();
                fis.close();
            } else {
                file.createNewFile();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // these lines define the bookAdd method of the Library class.
    // this method adds a new Book object to the books ArrayList.
    // after adding the book, it displays a message to the user indicating that the book has been added and writes the updated list of books to the "books.txt" file.
    public void bookAdd(String title, String author, int pageCount, int publicationYear) {
        Book book = new Book(title, author, pageCount, publicationYear);
        books.add(book);
        JOptionPane.showMessageDialog(null, "Book added: " + book.toString());
        try {
            FileOutputStream fos = new FileOutputStream("books.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(books);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // these lines define the bookDelete method of the Library class.
    // this method removes the specified Book object from the books ArrayList.
    // after deleting the book, it displays a message to the user indicating that the book has been deleted and writes the updated list of books to the "books.txt" file.
    public void bookDelete(Book book) {
        books.remove(book);
        JOptionPane.showMessageDialog(null, "Book deleted: " + book.toString());
        try {
            FileOutputStream fos = new FileOutputStream("books.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(books);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // these lines define the bookList method of the Library class.
    // this method lists all the Book objects in the books ArrayList and displays them to the user.
    public void bookList() {
        StringBuilder list = new StringBuilder();
        for (Book book : books) {
            list.append(book.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, "Books:\n" + list);
    }
    // these lines define the bookSearch method of the Library class.
    // this method searches for Book objects in the books ArrayList that contain the specified keyword.
    // if the searched Book objects are not found, it displays a "Book not found" message to the user.
    // otherwise, it shows the searched Book objects in a list to the user.
    public void bookSearch(String keyword) {
        ArrayList<Book> searchedBooks = new ArrayList<>();
        for (Book book : books) {
            String bookInfo = book.toString().toLowerCase();
            if (bookInfo.contains(keyword.toLowerCase())) {
                searchedBooks.add(book);
            }
        }
        if (searchedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Book not found.");
        } else {
            StringBuilder list = new StringBuilder();
            for (Book book : searchedBooks) {
                list.append(book.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, "Searched books:\n" + list);
        }
    }
    // these lines enable the program to run.
    // users can navigate among options to perform book addition, deletion, listing, and searching operations.
    // depending on the user's choice, the relevant methods of the Library class are called.
    public static void main(String[] args) {
        Library library = new Library("Sample Library", 2000);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 - Add Book");
            System.out.println("2 - Delete Book");
            System.out.println("3 - List Books");
            System.out.println("4 - Search for Books");
            System.out.println("5 - Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Book Title: ");
                String title = scanner.nextLine();
                System.out.print("Author: ");
                String author = scanner.nextLine();
                System.out.print("Page Count: ");
                int pageCount = scanner.nextInt();
                System.out.print("Publication Year: ");
                int publicationYear = scanner.nextInt();
                library.bookAdd(title, author, pageCount, publicationYear);
            } else if (choice == 2) {
                library.bookList();
                System.out.print("Enter the number of the book to delete: ");
                int index = scanner.nextInt();
                library.bookDelete(library.books.get(index - 1));
            } else if (choice == 3) {
                library.bookList();
            } else if (choice == 4) {
                System.out.print("Enter book title or author to search: ");
                String keyword = scanner.nextLine();
                library.bookSearch(keyword);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
// these lines define the Book class.
// this class stores the properties of books added to the library.
// it includes information such as book title, author, page count, and publication year.
// when an object is created, these properties are initialized.
// the toString() method is used to print the properties of a book in a readable format.
class Book implements Serializable {
    private final String title;
    private final String author;
    private final int pageCount;
    private final int publicationYear;

    public Book(String title, String author, int pageCount, int publicationYear) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public String toString() {
        return title + " - " + author + " - " + pageCount + " pages - " + publicationYear;
    }
}
