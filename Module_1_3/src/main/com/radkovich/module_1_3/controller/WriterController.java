package main.com.radkovich.module_1_3.controller;

import main.com.radkovich.module_1_3.model.Post;
import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.model.Writer;
import main.com.radkovich.module_1_3.repository.WriterRepository;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository;

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer createWriter(String firstName, String lastName, List<Post> posts, Status status){
        Writer newWriter = new Writer(firstName, lastName, posts, status);

        return writerRepository.save(newWriter);
    }

    public Writer getWriterById(Long id){
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters(){
        return writerRepository.getAll();
    }

    public Writer updateWriter(Long id, String firstName, String lastName, List<Post> posts, Status status){

        Writer updateWriter = new Writer(firstName, lastName, posts, status);

        updateWriter.setId(id);

        return writerRepository.update(updateWriter);
    }

    public void deleteWriter(Long id){
        writerRepository.deleteById(id);
    }

    public Writer addPostToWriter(Long writerId, Post post) {
        Writer writer = writerRepository.getById(writerId);
        writer.addPost(post);
        return writerRepository.update(writer);
    }
}
