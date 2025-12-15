package main.com.radkovich.module_1_3.controller;

import main.com.radkovich.module_1_3.model.Label;
import main.com.radkovich.module_1_3.model.Post;
import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String content, LocalDateTime creationDate, List<Label> labels, Status status) {
        Post newPost = new Post(content, LocalDateTime.now(), labels, status);
        return postRepository.save(newPost);
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);

    }
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }

    public Post updatePost(Long id, String content, LocalDateTime modificationDate, Status status){
        Post postToUpdate = new Post(content, modificationDate, status);
        postToUpdate.setId(id);
        return postRepository.save(postToUpdate);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post addLabelToPost(Long postId, Label label){
        Post post = postRepository.getById(postId);
        post.addLabel(label);
        return postRepository.update(post);
    }
}
