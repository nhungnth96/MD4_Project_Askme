package project.askme.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private Long id;
    private Category category;
    private Long userId;
    private String userFullName;
    private String userAvatar;
    private Date createdDate;
    private Date editedDate;
    private Date closedDate;
    private String title;
    private String body;
    private String image;
    private int score;
    private int viewCount;
    private int answerCount;
    private int commentCount;
    private int favoriteCount;
    private int voteCount;
    private int status;
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }


    public Question(Long id, Category category, Long userId, String userFullName, String userAvatar, Date createdDate, Date editedDate, Date closedDate, String title, String body, String image, int score, int viewCount, int answerCount, int commentCount, int favoriteCount, int voteCount, int status, List<Answer> answers) {
        this.id = id;
        this.category = category;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userAvatar = userAvatar;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.closedDate = closedDate;
        this.title = title;
        this.body = body;
        this.image = image;
        this.score = score;
        this.viewCount = viewCount;
        this.answerCount = answerCount;
        this.commentCount = commentCount;
        this.favoriteCount = favoriteCount;
        this.voteCount = voteCount;
        this.status = status;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getUserId() {
        return userId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
