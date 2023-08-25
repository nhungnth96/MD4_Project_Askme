package project.askme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import project.askme.dto.FormAnswerDto;
import project.askme.dto.FormQuestDto;
import project.askme.model.Answer;
import project.askme.model.Question;
import project.askme.model.User;
import project.askme.service.IGenericService;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService implements IGenericService<Answer,Long> {
    @Autowired
    DataSource dataSource;

    @Override
    public List<Answer> findAll() {
        return null;
    }

    public List<Answer> findAllByQuestId(Long questionId) {
        List<Answer> answers = new ArrayList<>();
        Connection con = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_getAnswerList(?)}");
            callSt.setLong(1,questionId);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getLong("id"));
                answer.setQuestionId(rs.getLong("question_id"));
                answer.setUserId(rs.getLong("user_id"));
                answer.setUserFullName(rs.getString("user_full_name"));
                answer.setUserAvatar(rs.getString("user_avatar"));
                answer.setCreatedDate(rs.getDate("created_date"));
                answer.setEditedDate(rs.getDate("edited_date"));
                answer.setBody(rs.getString("body"));
                answer.setImage(rs.getString("image"));
                answer.setScore(rs.getInt("score"));
                answer.setCommentCount(rs.getInt("comment_count"));
                answer.setVoteCount(rs.getInt("vote_count"));
                answer.setAccepted(rs.getInt("accepted"));
                answers.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return answers;
    }

    @Override
    public Answer findById(Long id) {
        return null;
    }
    @Value("${uploadImagePath}")
    private String uploadImagePath;
    public void formToModel(FormAnswerDto formAnswerDto, Long questionId, User user) {
        MultipartFile imageFile = formAnswerDto.getImage();
        String image = imageFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(imageFile.getBytes(), new File(uploadImagePath + image));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setUserId(user.getId());
        answer.setUserFullName(user.getFullName());
        answer.setUserAvatar(user.getAvatar());
        answer.setImage(image);
        answer.setBody(formAnswerDto.getBody());
        save(answer);
    }
    @Override
    public void save(Answer answer) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            if (answer.getId() == null) {
                CallableStatement callSt = con.prepareCall("{call proc_insertAnswer(?,?,?,?,?,?,?)}");
                callSt.setLong(1,answer.getQuestionId());
                callSt.setLong(2,answer.getUserId());
                callSt.setString(3,answer.getUserFullName());
                callSt.setString(4,answer.getUserAvatar());
                callSt.setString(5,answer.getBody());
                callSt.setString(6,answer.getImage());
                callSt.registerOutParameter(7, Types.INTEGER);
                callSt.execute();
                Long newAnswerId = callSt.getLong(7);
                CallableStatement callSt1 = con.prepareCall("{call proc_insertImage(?,?)}");
                callSt1.setString(1, answer.getImage());
                callSt1.setLong(2, answer.getUserId());
                callSt1.executeUpdate();
            } else {
                CallableStatement callSt = con.prepareCall("{call proc_updateAnswer(?,?,?)}");
                callSt.setString(1,answer.getBody());
                callSt.setString(2,answer.getImage());
                callSt.setLong(3,answer.getId());
                callSt.executeUpdate();
                CallableStatement callSt1 = con.prepareCall("{call proc_changeImage(?,?)}");
                callSt1.setString(1, answer.getImage());
                callSt1.setLong(2, answer.getUserId());
                callSt1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void delete(Long aLong) {

    }
}
