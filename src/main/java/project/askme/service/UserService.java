package project.askme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.askme.dto.FormEditDto;
import project.askme.dto.FormLoginDto;
import project.askme.dto.FormRegisterDto;
import project.askme.model.User;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService implements IGenericService<User,Long>{
    @Autowired
    DataSource dataSource;
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_getUserList()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setRoleId(rs.getLong("role_id"));
                user.setStatus(rs.getBoolean("status"));
                user.setCreationDate(rs.getDate("created_date"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAvatar(rs.getString("avatar"));
                user.setAbout(rs.getString("about"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
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
        return users;
    }

    @Override
    public User findById(Long id) {
        Connection con = null;
        User user = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_findUserById(?)}");
            callSt.setLong(1, id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setRoleId(rs.getLong("role_id"));
                user.setStatus(rs.getBoolean("status"));
                user.setCreationDate(rs.getDate("created_date"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAvatar(rs.getString("avatar"));
                user.setAbout(rs.getString("about"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
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
        return user;
    }
    public void formToModel(FormRegisterDto formRegisterDto) {
        // chuyen doi dto sang model
        User user = new User();
        user.setFullName(formRegisterDto.getFullName());
        user.setEmail(formRegisterDto.getEmail());
        user.setUsername(formRegisterDto.getUsername());
        user.setPassword(formRegisterDto.getPassword());
        save(user);
    }
    @Override
    public void save(User user) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = null;
            if(user.getId()==null){
                callSt = con.prepareCall("{call proc_insertUser(?,?,?,?,?,?,?)}");
                callSt.setString(1, user.getFullName());
                callSt.setString(2, user.getEmail());
                callSt.setString(3, user.getAddress());
                callSt.setString(4, user.getAbout());
                callSt.setString(5, user.getUsername());
                callSt.setString(6, user.getPassword());
                callSt.registerOutParameter(7, Types.INTEGER);
                callSt.execute();
                Long newUserId = callSt.getLong(7);
                CallableStatement callSt1 = con.prepareCall("{call proc_insertAvatar(?,?)}");
                callSt1.setString(1, user.getAvatar());
                callSt1.setLong(2, newUserId);
                callSt1.executeUpdate();
            } else {
                callSt = con.prepareCall("{call proc_editProfile(?,?,?,?,?)}");
                callSt.setString(1, user.getFullName());
                callSt.setString(2, user.getAddress());
                callSt.setString(3, user.getAvatar());
                callSt.setString(4, user.getAbout());
                callSt.setLong(5, user.getId());
                callSt.executeUpdate();
                CallableStatement callSt1 = con.prepareCall("{call proc_changeAvatar(?,?)}");
                callSt1.setString(1, user.getAvatar());
                callSt1.setLong(2, user.getId());
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


    public User login(FormLoginDto formLoginDto) {
        User user = null;
        Connection con  = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callst = con.prepareCall("{call proc_login(?,?)}");
            callst.setString(1, formLoginDto.getUsername());
            callst.setString(2, formLoginDto.getPassword());
            ResultSet rs = callst.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getLong("id"));
                user.setRoleId(rs.getLong("role_id"));
                user.setStatus(rs.getBoolean("status"));
                user.setCreationDate(rs.getDate("created_date"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAvatar(rs.getString("avatar"));
                user.setAbout(rs.getString("about"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
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

        return user;
    }
    public boolean validateUsername(String username) {
        String regex = "^[a-zA-Z0-9._#?!@$%^&*-]{5,15}$";
        return Pattern.matches(regex, username);
    }

    public boolean validatePassword(String password) {

        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,12}$";
        return Pattern.matches(regex, password);
    }

    public boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9]+[A-Za-z0-9._%+-]*@[a-z]+(\\.[a-z]+)$";
        return Pattern.matches(regex, email);
    }

    public boolean checkExistUsername(String username){
        Connection con = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_findUserByUsername(?)}");
            callSt.setString(1, username);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return true;
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
        return false;
    }
    public boolean checkExistEmail(String email){
        Connection con = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_findUserByEmail(?)}");
            callSt.setString(1, email);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return true;
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
        return false;
    }
    @Override
    public void delete(Long aLong) {}
}
