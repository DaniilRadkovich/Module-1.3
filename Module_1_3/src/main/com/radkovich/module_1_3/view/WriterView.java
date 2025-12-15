package main.com.radkovich.module_1_3.view;

import main.com.radkovich.module_1_3.controller.WriterController;
import main.com.radkovich.module_1_3.model.Label;
import main.com.radkovich.module_1_3.model.Post;
import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.model.Writer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner;

    public WriterView(WriterController writerController) {
        this.writerController = writerController;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("----------------------------------------------------");
            System.out.println("Enter 1 to create a writer");
            System.out.println("Enter 2 to get the writer by ID");
            System.out.println("Enter 3 to get all writers");
            System.out.println("Enter 4 to update the writer");
            System.out.println("Enter 5 to delete the writer");
            System.out.println("Enter 6 to add post to the writer");
            System.out.println("Enter 0 to exit");
            System.out.println("Please, choose something!");
            System.out.println("----------------------------------------------------");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createWriter();
                    break;
                case 2:
                    getWriterById();
                    break;
                case 3:
                    getAllWriters();
                    break;
                case 4:
                    updateWriter();
                    break;
                case 5:
                    deleteWriter();
                    break;
                case 6:
                    addPostToWriter();
                    break;
                case 0:
                    System.out.println("The program shut down!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Try again!");
                    break;
            }
        }
    }

    private void createWriter() {
        System.out.println("Enter the first name of the writer");
        String firstName = scanner.nextLine();

        System.out.println("Enter the last name of the writer");
        String lastName = scanner.nextLine();

        List<Post> posts = new ArrayList<>();

        Status status = Status.ACTIVE;

        Writer newWriter = writerController.createWriter(firstName, lastName, posts, status);

        System.out.println("Writer created! Your ID is " + newWriter.getId());
    }

    private void getWriterById() {
        System.out.println("Enter the searching writer ID:");
        long searchingWriterId = scanner.nextLong();

        Writer writer = writerController.getWriterById(searchingWriterId);
        System.out.println("Your writer is " + writer);
    }

    private void getAllWriters() {
        List<Writer> writers = writerController.getAllWriters();
        System.out.println("Current list of writers:");
        for (Writer writer : writers) {
            System.out.println(writer.toString());
        }
    }

    private void updateWriter() {
        System.out.println("Enter writer ID to update:");
        long writerId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter the new first name of the writer");
        String newFirstName = scanner.nextLine();

        System.out.println("Enter the new last name of the writer");
        String newLastName = scanner.nextLine();

        List<Post> posts = new ArrayList<>();


        Status status = Status.ACTIVE;

        Writer updatedWriter = writerController.updateWriter(writerId, newFirstName, newLastName, posts, status);

        System.out.println("Writer updated! Your ID is" + updatedWriter.getId());
    }

    private void deleteWriter() {
        System.out.println("Enter writer ID to delete:");
        Long writerId = scanner.nextLong();
        writerController.deleteWriter(writerId);
        System.out.println("Writer deleted!");
    }

    private void addPostToWriter() {
        System.out.println("Enter writer ID to add a post to:");
        Long writerId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter post content: ");
        String postContent = scanner.nextLine();

        LocalDateTime creationDate = LocalDateTime.now();

        List<Label> labels = new ArrayList<>();

        Status status = Status.ACTIVE;

        Post newPost = new Post(postContent, creationDate, labels, status);
        Writer updatedWriter = writerController.addPostToWriter(writerId, newPost);
        System.out.println("The post added to the writer with ID: " + updatedWriter.getId());
    }
}
