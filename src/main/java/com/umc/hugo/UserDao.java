package com.umc.hugo;

import com.umc.hugo.model.Store.*;
import com.umc.hugo.model.User.*;
import com.umc.hugo.model.Food.*;
import com.umc.hugo.model.menu.*;
import com.umc.hugo.model.review.GetReviewRes;
import com.umc.hugo.model.review.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //GET
    public List<GetUserRes> userRes(){
        return this.jdbcTemplate.query("Select * from test.Users",
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("profileImgUrl"))
        );
    }

    public List<GetFoodRes> foodRes(){
        return this.jdbcTemplate.query("Select * from Baemin.Food",
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("foodIdx"),
                        rs.getString("name"),
                        rs.getString("foodImgUrl"))
        );
    }

    public List<GetStoreRes> storeResByStar(){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar, " +
                        "S.starNum, ifnull(shortMenu.shortMenuMsg,0) as shortMenuMsg, " +
                        "S.leastPriceMsg, substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) as deliveryTipMsg, S.deliveryTimeMsg " +
                        "FROM Store S " +
                        "LEFT JOIN (SELECT storeIdx, GROUP_CONCAT(name) as shortMenuMsg " +
                        "            FROM Menu " +
                        "            GROUP BY storeIdx) shortMenu " +
                        "ON S.storeIdx = shortMenu.storeIdx " +
                        "ORDER BY S.storeStar desc",
                (rs, rowNum) -> new GetStoreRes(
                        rs.getString("storeImgUrl"),
                        rs.getString("name"),
                        rs.getString("availableWay"),
                        rs.getString("storeStar"),
                        rs.getString("starNum"),
                        rs.getString("shortMenuMsg"),
                        rs.getString("leastPriceMsg"),
                        rs.getString("deliveryTipMsg"),
                        rs.getString("deliveryTimeMsg"))
        );
    }
    public List<GetStoreRes> storeResByReview(){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar, " +
                        "S.starNum, ifnull(shortMenu.shortMenuMsg,0) as shortMenuMsg, " +
                        "S.leastPriceMsg, substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) as deliveryTipMsg, S.deliveryTimeMsg " +
                        "FROM Store S " +
                        "LEFT JOIN (SELECT storeIdx, GROUP_CONCAT(name) as shortMenuMsg " +
                        "            FROM Menu " +
                        "            GROUP BY storeIdx) shortMenu " +
                        "ON S.storeIdx = shortMenu.storeIdx " +
                        "ORDER BY S.reviewNum desc",
                (rs, rowNum) -> new GetStoreRes(
                        rs.getString("storeImgUrl"),
                        rs.getString("name"),
                        rs.getString("availableWay"),
                        rs.getString("storeStar"),
                        rs.getString("starNum"),
                        rs.getString("shortMenuMsg"),
                        rs.getString("leastPriceMsg"),
                        rs.getString("deliveryTipMsg"),
                        rs.getString("deliveryTimeMsg"))
        );
    }
    public List<GetStoreRes> storeResByIdx(){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar, " +
                        "S.starNum, ifnull(shortMenu.shortMenuMsg,0) as shortMenuMsg, " +
                        "S.leastPriceMsg, substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) as deliveryTipMsg, S.deliveryTimeMsg " +
                        "FROM Store S " +
                        "LEFT JOIN (SELECT storeIdx, GROUP_CONCAT(name) as shortMenuMsg " +
                        "            FROM Menu " +
                        "            GROUP BY storeIdx) shortMenu " +
                        "ON S.storeIdx = shortMenu.storeIdx " +
                        "ORDER BY S.storeIdx ",
                (rs, rowNum) -> new GetStoreRes(
                        rs.getString("storeImgUrl"),
                        rs.getString("name"),
                        rs.getString("availableWay"),
                        rs.getString("storeStar"),
                        rs.getString("starNum"),
                        rs.getString("shortMenuMsg"),
                        rs.getString("leastPriceMsg"),
                        rs.getString("deliveryTipMsg"),
                        rs.getString("deliveryTimeMsg"))
        );
    }


    public List<GetMenuRes> menuRes(int store){

            return this.jdbcTemplate.query("SELECT S.name as storeName, M.name, M.menuImgUrl, ifnull(M.menuInfoMsg,0) as menuInfoMsg, M.menuPrice " +
                            "FROM Store S " +
                            "LEFT JOIN Menu M " +
                            "on S.storeIdx = M.storeIdx " +
                            "WHERE S.storeIdx = ?",
                    (rs, rowNum) -> new GetMenuRes(
                            rs.getString("storeName"),
                            rs.getString("name"),
                            rs.getString("menuImgUrl"),
                            rs.getString("menuInfoMsg"),
                            rs.getInt("menuPrice"))
            , store);


    }

    public List<GetReviewRes> reviewRes(int store){

        return this.jdbcTemplate.query("SELECT S.name as storeName, U.name, R.reviewStar, R.reviewImgUrl, R.reviewMsg " +
                        "FROM Review R " +
                        "INNER JOIN User U ON R.userIdx = U.userIdx " +
                        "INNER JOIN Store S on R.storeIdx = S.storeIdx " +
                        "WHERE R.storeIdx = ?",

                (rs, rowNum) -> new GetReviewRes(
                        rs.getString("storeName"),
                        rs.getString("name"),
                        rs.getFloat("reviewStar"),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewMsg"))
                , store);


    }

    //POST
    public int addUser(PostUserReq postuserReq){
        String createUserQuery = "insert into test.Users (name, email, profileImgUrl) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{
                postuserReq.getName(), postuserReq.getEmail(), postuserReq.getProfileImgUrl()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    public int addFood(PostFoodReq postfoodReq){
        String createUserQuery = "insert into Baemin.Food (name,foodImgUrl) VALUES (?,?)";
        Object[] createUserParams = new Object[]{
                postfoodReq.getName(), postfoodReq.getFoodImgUrl()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    public int addStore(PostStoreReq poststoreReq){
        String createUserQuery = "insert into Baemin.Store (foodIdx,name,storeImgUrl,storeInfoMsg,availableWay," +
                "storeStar,starNum,reviewNum,deliveryTimeMsg,leastPriceMsg,deliveryTipMsg) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                poststoreReq.getFoodIdx(), poststoreReq.getName(),poststoreReq.getStoreImgUrl(),poststoreReq.getStoreInfoMsg(),poststoreReq.getAvailableWay(),
                poststoreReq.getStoreStar(),poststoreReq.getStarNum(),poststoreReq.getReviewNum(),poststoreReq.getDeliveryTimeMsg(),
                poststoreReq.getLeastPriceMsg(),poststoreReq.getDeliveryTipMsg()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
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

    public int addReview(PostReviewReq postreviewReq){
        String createUserQuery = "insert into Baemin.Review (userIdx,storeIdx,reviewImgUrl,reviewStar,reviewMsg) " +
                "VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postreviewReq.getUserIdx(), postreviewReq.getStoreIdx(), postreviewReq.getReviewImgUrl(), postreviewReq.getReviewStar(),
                postreviewReq.getReviewMsg()
        };
        this.jdbcTemplate.update(createUserQuery,createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }



}