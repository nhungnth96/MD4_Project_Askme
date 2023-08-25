package project.askme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.askme.model.Category;
import project.askme.model.User;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements IGenericService<Category,Long>{
    @Autowired
    DataSource dataSource;
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        Connection con = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_getCategoryList()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
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
        return categories;
    }

    @Override
    public Category findById(Long id) {
        Connection con = null;
        Category category = null;
        try {
            con = dataSource.getConnection();
            CallableStatement callSt = con.prepareCall("{call proc_findCategoryById(?)}");
            callSt.setLong(1,id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return category;
    }

    @Override
    public void save(Category category) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            if (category.getId() == null) {
                CallableStatement callSt = con.prepareCall("{call proc_insertCategory(?)}");
                callSt.setString(1,category.getName());
                callSt.executeUpdate();
            } else {
                CallableStatement callSt = con.prepareCall("{call proc_updateCategory(?,?)}");
                callSt.setString(1,category.getName());
                callSt.setLong(2,category.getId());
                callSt.executeUpdate();
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
    public void delete(Long id) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            // proc phải xóa hết khóa phụ
            CallableStatement callSt = con.prepareCall("{call proc_deleteCategory(?)}");
            callSt.setLong(1,id);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
