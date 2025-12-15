package main.com.radkovich.module_1_3.view;

import main.com.radkovich.module_1_3.controller.LabelController;
import main.com.radkovich.module_1_3.model.Label;
import main.com.radkovich.module_1_3.model.Status;

import java.util.List;
import java.util.Scanner;

public class LabelView {
    private final LabelController labelController;
    private final Scanner scanner;

    public LabelView(LabelController labelController) {
        this.labelController = labelController;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("----------------------------------------------------");
            System.out.println("Enter 1 to create a label");
            System.out.println("Enter 2 to get the label by ID");
            System.out.println("Enter 3 to get all labels");
            System.out.println("Enter 4 to update the label");
            System.out.println("Enter 5 to delete the label");
            System.out.println("Enter 0 to exit");
            System.out.println("Please, choose something!");
            System.out.println("----------------------------------------------------");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createLabel();
                    break;
                case 2:
                    getLabelById();
                    break;
                case 3:
                    getAllLabels();
                    break;
                case 4:
                    updateLabel();
                    break;
                case 5:
                    deleteLabel();
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

    private void createLabel() {
        System.out.println("Enter label name:");
        String labelName = scanner.nextLine();
        Status status = Status.ACTIVE;

        Label newLabel = labelController.createLabel(labelName, status);
        System.out.println("Label created! Your ID is " + newLabel.getId());
    }

    private void getLabelById() {
        System.out.println("Enter the searching label ID:");
        long searchingLabelId = scanner.nextLong();

        Label label = labelController.getLabelById(searchingLabelId);
        System.out.println("Your label is " + label);
    }

    private void getAllLabels() {
        List<Label> labels = labelController.getAllLabels();
        System.out.println("Current list of labels:");
        for (Label label : labels) {
            System.out.println(label.toString());
        }
    }

    private void updateLabel() {
        System.out.println("Enter label ID to update:");
        long labelId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter new label name:");
        String newLabelName = scanner.nextLine();

        Status status = Status.ACTIVE;

        Label updatedLabel = labelController.updateLabel(labelId, newLabelName, status);
        System.out.println("Label updated! Your ID is" + updatedLabel.getId());
    }

    private void deleteLabel() {
        System.out.println("Enter label ID to delete:");
        Long labelId = scanner.nextLong();
        labelController.deleteLabel(labelId);
        System.out.println("Label deleted!");
    }
}
