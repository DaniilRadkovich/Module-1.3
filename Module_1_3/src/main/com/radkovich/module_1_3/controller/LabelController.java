package main.com.radkovich.module_1_3.controller;

import main.com.radkovich.module_1_3.model.Label;
import main.com.radkovich.module_1_3.model.Status;
import main.com.radkovich.module_1_3.repository.LabelRepository;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository;

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label createLabel(String name, Status status) {
        Label newLabel = new Label(name, status);
        return labelRepository.save(newLabel);
    }

    public Label getLabelById(Long id) {
        return labelRepository.getById(id);
    }

    public List<Label> getAllLabels() {
        return labelRepository.getAll();
    }

    public Label updateLabel(Long id, String name, Status status) {
        Label updateLabel = new Label(name, status);
        updateLabel.setId(id);

        return labelRepository.update(updateLabel);
    }

    public void deleteLabel(Long id) {
        labelRepository.deleteById(id);
    }
}
