package project.askme.dto;
import org.springframework.web.multipart.MultipartFile;
import project.askme.model.Category;

public class FormQuestDto {
    private Long questId;
    private Long userId;
    private String userFullName;
    private String userAvatar;
    private String title;
    private Long categoryId;
    private MultipartFile image;
    private String body;

    public FormQuestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public FormQuestDto(String title, Long categoryId, MultipartFile image, String body) {
        this.title = title;
        this.categoryId = categoryId;
        this.image = image;
        this.body = body;
    }

    public FormQuestDto(Long userId, String userFullName, String userAvatar, String title, Long categoryId, MultipartFile image, String body) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userAvatar = userAvatar;
        this.title = title;
        this.categoryId = categoryId;
        this.image = image;
        this.body = body;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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
}
