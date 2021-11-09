package com.umc.hugo.food;

import com.umc.hugo.food.model.GetFoodRes;
import com.umc.hugo.food.model.PostFoodReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FoodDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //GET
    public List<GetFoodRes> foodRes(){
        return this.jdbcTemplate.query("Select * from Baemin.Food",
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("foodIdx"),
                        rs.getString("name"),
                        rs.getString("foodImgUrl"))
        );
    }

    //POST
    public int addFood(PostFoodReq postfoodReq){
        String createUserQuery = "insert into Baemin.Food (name,foodImgUrl) VALUES (?,?)";
        Object[] createUserParams = new Object[]{
                postfoodReq.getName(), postfoodReq.getFoodImgUrl()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }
}
