package main.com.radkovich.module_1_3.view;

import main.com.radkovich.module_1_3.controller.LabelController;
import main.com.radkovich.module_1_3.controller.PostController;
import main.com.radkovich.module_1_3.model.Label;
import main.com.radkovich.module_1_3.model.Post;
import main.com.radkovich.module_1_3.model.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final Scanner scanner;

    public PostView(PostController postController) {
        this.postController = postController;
        this.scanner = new Scanner(System.in);
    }


    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("----------------------------------------------------");
            System.out.println("Enter 1 to create a post");
            System.out.println("Enter 2 to get the post by ID");
            System.out.println("Enter 3 to get all posts");
            System.out.println("Enter 4 to update the post");
            System.out.println("Enter 5 to delete the post");
            System.out.println("Enter 6 to add label to the post");
            System.out.println("Enter 0 to exit");
            System.out.println("Please, choose something!");
            System.out.println("----------------------------------------------------");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createPost();
                    break;
                case 2:
                    getPostById();
                    break;
                case 3:
                    getAllPosts();
                    break;
                case 4:
                    updatePost();
                    break;
                case 5:
                    deletePost();
                    break;
                case 6:
                    addLabelToPost();
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

    private void createPost() {
        System.out.println("Enter post content:");
        String postContent = scanner.nextLine();
        System.out.println("DEBUG: Содержание поста, которое мы передаем: " + postContent);

        Status status = Status.ACTIVE;

        LocalDateTime creationDate = LocalDateTime.now();

        List<Label> labels = new ArrayList<>();

        Post newPost = postController.createPost(postContent, creationDate, labels, status); //TODO Check labels
        System.out.println("Post created! Your ID is " + newPost.getId());
    }

    private void getPostById() {
        System.out.println("Enter the searching post ID:");
        long searchingPostId = scanner.nextLong();

        Post post = postController.getPostById(searchingPostId);
        System.out.println("Your post is " + post);
    }

    private void getAllPosts() {
        List<Post> posts = postController.getAllPosts();
        System.out.println("Current list of posts:");
        for (Post post : posts) {
            System.out.println(post.toString());
        }
    }

    private void updatePost() {
        System.out.println("Enter post ID to update:");
        long postId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter new post content:");
        String newPostContent = scanner.nextLine();

        Status status = Status.ACTIVE;
        LocalDateTime modificationDate = LocalDateTime.now();

        Post updatedPost = postController.updatePost(postId, newPostContent, modificationDate, status);
        System.out.println("Post updated! Your ID is" + updatedPost.getId());
    }

    private void deletePost() {
        System.out.println("Enter post ID to delete:");
        Long postId = scanner.nextLong();
        postController.deletePost(postId);
        System.out.println("Post deleted!");
    }

    public void addLabelToPost() {
        System.out.println("Enter post ID to add a label to:");
        Long postId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter label name: ");
        String labelName = scanner.nextLine();

        Status status = Status.ACTIVE;

        Label newLabel = new Label(labelName, status);

        Post updatedPost = postController.addLabelToPost(postId, newLabel);

        System.out.println("The label added to the post with ID: " + updatedPost.getId());
    }
}

