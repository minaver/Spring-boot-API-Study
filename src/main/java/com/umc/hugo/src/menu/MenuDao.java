package com.umc.hugo.src.menu;

import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.menu.model.*;
import com.umc.hugo.src.store.Store;
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

    public List<GetMenuRes> menuRes(int store, int page, int pageSize){

        int startPage = (page-1)*pageSize;

        return this.jdbcTemplate.query("SELECT M.name, M.menuImgUrl, ifnull(M.menuInfoMsg,0) as menuInfoMsg, M.menuPrice " +
                        "FROM Store S " +
                        "LEFT JOIN Menu M " +
                        "on S.storeIdx = M.storeIdx " +
                        "WHERE S.storeIdx = ? " +
                        "LIMIT ?,?",
                (rs, rowNum) -> new GetMenuRes(
                        rs.getString("name"),
                        rs.getString("menuImgUrl"),
                        rs.getString("menuInfoMsg"),
                        rs.getInt("menuPrice"))
                , store,startPage,pageSize);
    }

    // Get Food Info
    public Food getFood(int storeIdx){
        String getFoodQuery = "SELECT F.name, F.foodImgUrl, F.status From Food F INNER JOIN Store S ON F.foodIdx = S.foodIdx WHERE S.storeIdx = ?";

        return (Food) this.jdbcTemplate.queryForObject(getFoodQuery,
                (rs,rowNum) -> new Food(
                        rs.getString("name"),
                        rs.getString("foodImgUrl"),
                        rs.getString("status")
                )

        ,storeIdx);

    }

    // Get Store Info
    public Store getStore(int storeIdx){
        String getStoreQuery = "SELECT S.foodIdx, S.name, S.ownerIdx, S.storeImgUrl , S.storeInfoMsg, S.availableWay, S.orderNum, S.storeStar, " +
                                    "S.starNum, S.reviewNum, " +
                                    "S.deliveryTimeMsg, S.leastPriceMsg, " +
                                    "substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) deliveryTipMsg, S.status " +
                                    "From Store S " +
                                    "Where S.storeIdx = ? and S.status = 'active'";

        return (Store) this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new Store(
                        rs.getInt("foodIdx"),
                        rs.getString("name"),
                        rs.getInt("ownerIdx"),
                        rs.getString("storeImgUrl"),
                        rs.getString("storeInfoMsg"),
                        rs.getString("availableWay"),
                        rs.getInt("orderNum"),
                        rs.getFloat("storeStar"),
                        rs.getInt("starNum"),
                        rs.getInt("reviewNum"),
                        rs.getString("deliveryTimeMsg"),
                        rs.getInt("leastPriceMsg"),
                        rs.getString("deliveryTipMsg"),
                        rs.getString("status")
                )
        ,storeIdx);
    }

    public MenuNum getMenuNum(int storeIdx){
        String getMenuNumQuery = "SELECT count(*) as menuNum From Menu WHERE storeIdx = ?";

        return (MenuNum) this.jdbcTemplate.queryForObject(getMenuNumQuery,
                (rs,rowNum) -> new MenuNum(
                        rs.getInt("menuNum")
                ),storeIdx);
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

    //PATCH
    // 1. ?????? ?????? ??????
    public int modifyMenuName(PatchMenuReq patchMenuReq) {
        String modifyMenuNameQuery = "update Menu set name = ? where menuIdx = ? ";
        Object[] modifyMenuNameParams = new Object[]{patchMenuReq.getName(), patchMenuReq.getMenuIdx()};

        return this.jdbcTemplate.update(modifyMenuNameQuery, modifyMenuNameParams); // ???????????? ???????????? ?????? ??????(??????????????? 1, ??????????????? 0)
    }

    // 2. ?????? ImgUrl ??????
    public int modifyMenuImgUrl(PatchMenuImgUrlReq patchMenuImgUrlReq) {
        String modifyMenuImgUrlQuery = "update Menu set menuImgUrl = ? where menuIdx = ? ";
        Object[] modifyMenuImgUrlParams = new Object[]{patchMenuImgUrlReq.getMenuImgUrl(), patchMenuImgUrlReq.getMenuIdx()};

        return this.jdbcTemplate.update(modifyMenuImgUrlQuery, modifyMenuImgUrlParams); // ???????????? ???????????? ?????? ??????(??????????????? 1, ??????????????? 0)
    }

    // 3. ?????? status ??????
    public int modifyMenuStatus(PatchMenuStatusReq patchMenuStatusReq) {
        String modifyMenuStatusQuery = "update Menu set status = ? where menuIdx = ? ";
        Object[] modifyMenuStatusParams = new Object[]{patchMenuStatusReq.getStatus(), patchMenuStatusReq.getMenuIdx()};

        return this.jdbcTemplate.update(modifyMenuStatusQuery, modifyMenuStatusParams); // ???????????? ???????????? ?????? ??????(??????????????? 1, ??????????????? 0)
    }


}
