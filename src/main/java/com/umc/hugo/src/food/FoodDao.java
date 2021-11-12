package com.umc.hugo.src.food;

import com.umc.hugo.src.food.model.*;
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
        return this.jdbcTemplate.query("Select * from Baemin.Food WHERE status = 'active' ",
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

    //PATCH
    // 1. 음식 이름 수정
    public int modifyFoodName(PatchFoodReq patchFoodReq) {
        String modifyFoodNameQuery = "update Food set name = ? where foodIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyFoodNameParams = new Object[]{patchFoodReq.getName(), patchFoodReq.getFoodIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyFoodNameQuery, modifyFoodNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 2. 음식 ImgUrl 수정
    public int modifyFoodImgUrl(PatchFoodImgUrlReq patchFoodImgUrlReq) {
        String modifyFoodImgUrlQuery = "update Food set foodImgUrl = ? where foodIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyFoodImgUrlParams = new Object[]{patchFoodImgUrlReq.getFoodImgUrl(), patchFoodImgUrlReq.getFoodIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyFoodImgUrlQuery, modifyFoodImgUrlParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 3. 음식 status 수정
    public int modifyFoodStatus(PatchFoodStatusReq patchFoodStatusReq) {
        String modifyFoodStatusQuery = "update Food set status = ? where foodIdx = ? ";
        Object[] modifyFoodStatusParams = new Object[]{patchFoodStatusReq.getStatus(), patchFoodStatusReq.getFoodIdx()};

        return this.jdbcTemplate.update(modifyFoodStatusQuery, modifyFoodStatusParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }


}
