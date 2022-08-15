package com.example.todolist.todo.services;

import com.example.todolist.todo.data.ImageResponse;
import com.example.todolist.todo.entities.ImageEntity;
import com.example.todolist.todo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final TodoService todoService;

    @Autowired
    public ImageService(ImageRepository imageRepository, TodoService todoService) {
        this.imageRepository = imageRepository;
        this.todoService = todoService;
    }


    public ImageEntity uploadFile(MultipartFile file, UUID todoId) throws Exception {
        try {
            var todo = todoService.get(todoId);

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid sequence");
            }

            var attachment = new ImageEntity(
                    fileName,
                    file.getContentType(),
                    file.getBytes(),
                    todo
            );
            return imageRepository.save(attachment);
        } catch (Exception ex) {
            throw new Exception("Could not create a file");
        }
    }

    public ImageEntity get(UUID imageId) throws Exception {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new Exception("Cannot find an image"));
    }
}
