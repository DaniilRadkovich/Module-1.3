package main.com.radkovich.module_1_3;

import main.com.radkovich.module_1_3.controller.LabelController;
import main.com.radkovich.module_1_3.controller.PostController;
import main.com.radkovich.module_1_3.controller.WriterController;
import main.com.radkovich.module_1_3.repository.LabelRepository;
import main.com.radkovich.module_1_3.repository.PostRepository;
import main.com.radkovich.module_1_3.repository.WriterRepository;
import main.com.radkovich.module_1_3.repository.gson.GsonLabelRepositoryImpl;
import main.com.radkovich.module_1_3.repository.gson.GsonPostRepositoryImpl;
import main.com.radkovich.module_1_3.repository.gson.GsonWriterRepositoryImpl;
import main.com.radkovich.module_1_3.view.LabelView;
import main.com.radkovich.module_1_3.view.PostView;
import main.com.radkovich.module_1_3.view.WriterView;

import java.util.Scanner;

public class AppContext {
    Scanner scanner = new Scanner(System.in);

    public void launch(){
        System.out.println("Choose an option:");
        System.out.println("1: Writer");
        System.out.println("2: Post");
        System.out.println("3: Label");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch(option){
            case 1: writerStart(); break;
            case 2: postStart(); break;
            case 3: LabelStart(); break;
            default: System.out.println("Invalid option");
        }
    }
    public void writerStart(){
        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        WriterController writerController = new WriterController(writerRepository);
        WriterView writerView = new WriterView(writerController);
        writerView.run();
    }
    public void postStart(){
        PostRepository postRepository = new GsonPostRepositoryImpl();
        PostController postController = new PostController(postRepository);
        PostView postView = new PostView(postController);
        postView.run();
    }
    public void LabelStart(){
        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
        LabelController labelController = new LabelController(labelRepository);
        LabelView labelView = new LabelView(labelController);
        labelView.run();
    }

}
