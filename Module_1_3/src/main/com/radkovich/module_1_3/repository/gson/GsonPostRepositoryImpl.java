package main.com.radkovich.module_1_3.repository.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import main.com.radkovich.module_1_3.exception.NotFoundException;
import main.com.radkovich.module_1_3.model.Label;
import main.com.radkovich.module_1_3.model.Post;
import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.repository.LabelRepository;
import main.com.radkovich.module_1_3.repository.PostRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GsonPostRepositoryImpl implements PostRepository {

    private static final String POST_FILE_NAME = "src/main/resources/posts.json";
    private final Gson gson;
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    public GsonPostRepositoryImpl() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.format(formatter)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, typeOfT, context) -> json == null ? null : LocalDateTime.parse(json.getAsString(), formatter))
                .create();
    }

    private List<Post> loadPosts() {
        try (Reader reader = new FileReader(POST_FILE_NAME)) {
            Type type = new TypeToken<List<Post>>() {
            }.getType();
            List<Post> posts = gson.fromJson(reader, type);
            if (posts == null) {
                posts = new ArrayList<>();
            }
            return posts;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private void savePosts(List<Post> posts) {
        try (FileWriter writer = new FileWriter(POST_FILE_NAME)) {
            gson.toJson(posts, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getById(Long id) {
        return loadPosts().stream()
                .filter(post -> Objects.equals(post.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Post with id: " + id + " not found!"));
    }

    @Override
    public List<Post> getAll() {
        return loadPosts();
    }

    @Override
    public Post save(Post post) {
        List<Post> currentPosts = loadPosts();

        long nextId = IdGenerator.generateNextId(currentPosts, Post::getId);

        post.setId(nextId);
        currentPosts.add(post);
        savePosts(currentPosts);

        return post;
    }

    @Override
    public Post update(Post postToUpdate) {
        List<Post> currentPosts = loadPosts();
        List<Post> updatedPosts = currentPosts.stream()
                .map(existingPost -> {
                    if (Objects.equals(existingPost.getId(), postToUpdate.getId())) {
                        existingPost.setContent(postToUpdate.getContent());
                        existingPost.setUpdated(postToUpdate.getUpdated());
                        existingPost.setLabels(postToUpdate.getLabels());
                        existingPost.setStatus(postToUpdate.getStatus());

                        for (Label label : postToUpdate.getLabels()) {
                            labelRepository.update(label);
                            labelRepository.save(label);
                        }
                    }
                    return existingPost;
                }).toList();
        savePosts(updatedPosts);
        return postToUpdate;
    }

    @Override
    public void deleteById(Long id) {
        List<Post> currentPosts = loadPosts();
        List<Post> deletePosts = currentPosts.stream()
                .map(existingPost -> {
                    if (Objects.equals(existingPost.getId(), id)) {
                        existingPost.setStatus(Status.DELETED);
                    }
                    return existingPost;
                }).toList();
        savePosts(deletePosts);
    }
}
