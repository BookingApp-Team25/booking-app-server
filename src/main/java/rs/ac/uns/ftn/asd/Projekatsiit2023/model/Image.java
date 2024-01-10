package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "imagePath",nullable = false)
    private String imagePath;

    public Image() {
    }

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public UUID getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getEncodedImage() throws IOException {
        byte[] imageBytes = Files.readAllBytes(Path.of(this.imagePath));
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64Image;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
