package main.com.radkovich.module_1_3.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.com.radkovich.module_1_3.exception.NotFoundException;
import main.com.radkovich.module_1_3.model.Label;

import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.repository.LabelRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private static final String LABEL_FILE_NAME = "src/main/resources/labels.json";
    private final Gson gson = new Gson();

    private List<Label> loadLabels() {
        try (Reader reader = new FileReader(LABEL_FILE_NAME)) {
            Type type = new TypeToken<List<Label>>() {
            }.getType();
            List<Label> labels = gson.fromJson(reader, type);
            if (labels == null) {
                labels = new ArrayList<>();
            }
            return labels;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private void saveLabels(List<Label> labels) {
        try (FileWriter writer = new FileWriter(LABEL_FILE_NAME)) {
            gson.toJson(labels, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Label getById(Long id) {
        return loadLabels().stream()
                .filter(label -> Objects.equals(label.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Label with id: " + id + "not found!"));
    }

    @Override
    public List<Label> getAll() {
        return loadLabels();
    }

    @Override
    public Label save(Label label) {
        List<Label> currentLabels = loadLabels();

        long nextId = IdGenerator.generateNextId(currentLabels, Label::getId);

        label.setId(nextId);
        currentLabels.add(label);
        saveLabels(currentLabels);

        return label;
    }

    @Override
    public Label update(Label updateLabel) {
        List<Label> currentLabels = loadLabels();
        List<Label> updatedLabels = currentLabels.stream()
                .map(existingLabel -> {
                    if (Objects.equals(existingLabel.getId(), updateLabel.getId())) {
                        return updateLabel;
                    }
                    return existingLabel;
                }).toList();
        saveLabels(updatedLabels);
        return updateLabel;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> currentLabels = loadLabels();
        List<Label> deleteLabels = currentLabels.stream()
                .map(existingLabel -> {
                    if(Objects.equals(existingLabel.getId(), id)){
                        existingLabel.setStatus(Status.DELETED);
                    }
                    return existingLabel;
                }).toList();

        saveLabels(deleteLabels);
    }
}
