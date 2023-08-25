package project.askme.dto;

import org.springframework.web.multipart.MultipartFile;

public class FormQuestEditDto {
    private Long questId;
    private String title;
    private Long categoryId;
    private MultipartFile image;
    private String body;

    public FormQuestEditDto() {
    }

    public FormQuestEditDto(String title, Long categoryId, MultipartFile image, String body) {
        this.title = title;
        this.categoryId = categoryId;
        this.image = image;
        this.body = body;
    }

    public FormQuestEditDto(Long questId, String title, Long categoryId, MultipartFile image, String body) {
        this.questId = questId;
        this.title = title;
        this.categoryId = categoryId;
        this.image = image;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
        this.questId = questId;
    }
}
