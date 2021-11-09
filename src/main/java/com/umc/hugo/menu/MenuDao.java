package com.umc.hugo.menu;

import com.umc.hugo.food.Food;
import com.umc.hugo.menu.model.GetMenuRes;
import com.umc.hugo.menu.model.PostMenuReq;
import com.umc.hugo.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class MenuDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMenuRes> menuRes(int store){

        return this.jdbcTemplate.query("SELECT M.name, M.menuImgUrl, ifnull(M.menuInfoMsg,0) as menuInfoMsg, M.menuPrice " +
                        "FROM Store S " +
                        "LEFT JOIN Menu M " +
                        "on S.storeIdx = M.storeIdx " +
                        "WHERE S.storeIdx = ?",
                (rs, rowNum) -> new GetMenuRes(
                        rs.getString("name"),
                        rs.getString("menuImgUrl"),
                        rs.getString("menuInfoMsg"),
                        rs.getInt("menuPrice"))
                , store);
    }

    // Get Food Info
    public Food getFood(int storeIdx){
        String getFoodQuery = "SELECT F.name, F.foodImgUrl From Food F INNER JOIN Store S ON F.foodIdx = S.foodIdx WHERE S.storeIdx = ?";

        return (Food) this.jdbcTemplate.queryForObject(getFoodQuery,
                (rs,rowNum) -> new Food(
                        rs.getString("name"),
                        rs.getString("foodImgUrl"))
        ,storeIdx);

    }

    // Get Store Info
    public Store getStore(int storeIdx){
        String getStoreQuery = "SELECT S.foodIdx, S.name, S.storeImgUrl , S.storeInfoMsg, S.availableWay, S.storeStar, " +
                                    "S.starNum, S.reviewNum, " +
                                    "S.deliveryTimeMsg, S.leastPriceMsg, " +
                                    "substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) deliveryTipMsg " +
                                    "From Store S " +
                                    "Where S.storeIdx = ?";

        return (Store) this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new Store(
                        rs.getInt("foodIdx"),
                        rs.getString("name"),
                        rs.getString("storeImgUrl"),
                        rs.getString("storeInfoMsg"),
                        rs.getString("availableWay"),
                        rs.getFloat("storeStar"),
                        rs.getInt("starNum"),
                        rs.getInt("reviewNum"),
                        rs.getString("deliveryTimeMsg"),
                        rs.getInt("leastPriceMsg"),
                        rs.getString("deliveryTipMsg")
                )
        ,storeIdx);
    }

    public int addMenu(PostMenuReq postmenuReq){
        String createUserQuery = "insert into Baemin.Menu (storeIdx,name,menuImgUrl,menuInfoMsg,menuPrice) " +
                "VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postmenuReq.getStoreIdx(),postmenuReq.getName(),postmenuReq.getMenuImgUrl(),postmenuReq.getMenuInfoMsg(),
                postmenuReq.getMenuPrice()
        };
        this.jdbcTemplate.update(createUserQuery,createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }


}
