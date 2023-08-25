package project.askme.dto;

import org.springframework.web.multipart.MultipartFile;

public class FormEditDto {
    private String fullName;
    private String address;
    private MultipartFile avatar;
    private String about;

    public FormEditDto() {
    }

    public FormEditDto(String fullName, String address, MultipartFile avatar, String about) {
        this.fullName = fullName;
        this.address = address;
        this.avatar = avatar;
        this.about = about;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
