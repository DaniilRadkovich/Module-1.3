package main.com.radkovich.module_1_3.repository.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import main.com.radkovich.module_1_3.exception.NotFoundException;
import main.com.radkovich.module_1_3.model.Post;
import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.model.Writer;
import main.com.radkovich.module_1_3.repository.PostRepository;
import main.com.radkovich.module_1_3.repository.WriterRepository;

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

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final String WRITER_FILE_NAME = "src/main/resources/writers.json";
    private final Gson gson;
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public GsonWriterRepositoryImpl() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.format(formatter)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, typeOfT, context) -> json == null ? null : LocalDateTime.parse(json.getAsString(), formatter))
                .create();
    }

    private List<Writer> loadWriters() {
        try (Reader reader = new FileReader(WRITER_FILE_NAME)) {
            Type type = new TypeToken<List<Writer>>() {
            }.getType();
            List<Writer> writers = gson.fromJson(reader, type);
            if (writers == null) {
                writers = new ArrayList<>();
            }
            return writers;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private void saveWriters(List<Writer> writers) {
        try (FileWriter writer = new FileWriter(WRITER_FILE_NAME)) {
            gson.toJson(writers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer getById(Long id) {
        return loadWriters().stream()
                .filter(writer -> Objects.equals(writer.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Writer with id: " + id + "not found!"));
    }

    @Override
    public List<Writer> getAll() {
        return loadWriters();
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> currentWriters = loadWriters();

        long nextId = IdGenerator.generateNextId(currentWriters, Writer::getId);

        writer.setId(nextId);
        currentWriters.add(writer);
        saveWriters(currentWriters);

        return writer;
    }

    @Override
    public Writer update(Writer updateWriter) {
        List<Writer> currentWriters = loadWriters();
        List<Writer> updatedWriters = currentWriters.stream()
                .map(existingWriter -> {
                    if (Objects.equals(existingWriter.getId(), updateWriter.getId())) {
                        existingWriter.setFirstName(updateWriter.getFirstName());
                        existingWriter.setLastName(updateWriter.getLastName());
                        existingWriter.setPosts(updateWriter.getPosts());
                        existingWriter.setStatus(updateWriter.getStatus());

                        for (Post post : updateWriter.getPosts()) {
                            postRepository.update(post);
                            postRepository.save(post);
                        }
                    }
                    return existingWriter;
                }).toList();

        saveWriters(updatedWriters);
        return updateWriter;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> currentWriters = loadWriters();

        List<Writer> deleteWriters = currentWriters.stream()
                .map(exisitngWriter -> {
                    if (Objects.equals(exisitngWriter.getId(), id)) {
                        exisitngWriter.setStatus(Status.DELETED);
                    }
                    return exisitngWriter;
                }).toList();

        saveWriters(deleteWriters);
    }
}
