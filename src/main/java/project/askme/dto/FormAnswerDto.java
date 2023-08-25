package project.askme.dto;

import org.springframework.web.multipart.MultipartFile;

public class FormAnswerDto {
    private String title;
    private MultipartFile image;
    private String body;

    public FormAnswerDto() {
    }

    public FormAnswerDto(String title, MultipartFile image, String body) {
        this.title = title;
        this.image = image;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
