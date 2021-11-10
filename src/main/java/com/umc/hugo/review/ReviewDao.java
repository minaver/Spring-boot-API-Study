package com.umc.hugo.review;

import com.umc.hugo.food.Food;
import com.umc.hugo.review.model.GetReviewRes;
import com.umc.hugo.review.model.GetReviewResForStore;
import com.umc.hugo.review.model.GetReviewResForUser;
import com.umc.hugo.review.model.PostReviewReq;
import com.umc.hugo.store.Store;
import com.umc.hugo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // userIdx & storeIdx 둘다 들어왔을 때
    public List<GetReviewRes> reviewRes(int storeIdx, int userIdx){

        String getReviewQuery = "SELECT R.reviewStar, R.reviewImgUrl, R.reviewMsg " +
                "FROM Review R " +
                "INNER JOIN User U ON R.userIdx = U.userIdx " +
                "INNER JOIN Store S on R.storeIdx = S.storeIdx " +
                "WHERE R.storeIdx = ? and U.userIdx = ? ";
        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getFloat("reviewStar"),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewMsg"))
                ,userIdx,storeIdx);

    }

    // storeIdx 만 들어왔을 때 (각 List 마다 User name 추가 출력)
    public List<GetReviewResForStore> reviewResForStore(int storeIdx){

        String getReviewQuery = "SELECT U.name, R.reviewStar, R.reviewImgUrl, R.reviewMsg " +
                "FROM Review R " +
                "INNER JOIN User U ON R.userIdx = U.userIdx " +
                "INNER JOIN Store S on R.storeIdx = S.storeIdx " +
                "WHERE R.storeIdx = ? ";

        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewResForStore(
                        rs.getString("name"),
                        rs.getFloat("reviewStar"),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewMsg"))
                ,storeIdx);
    }

    // userIdx 만 들어왔을 때 (각 List 마다 Store name 추가 출력)
    public List<GetReviewResForUser> reviewResForUser(int userIdx){
        String getReviewQuery = "SELECT S.name, R.reviewStar, R.reviewImgUrl, R.reviewMsg " +
                "FROM Review R " +
                "INNER JOIN User U ON R.userIdx = U.userIdx " +
                "INNER JOIN Store S on R.storeIdx = S.storeIdx " +
                "WHERE U.userIdx = ? ";

        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewResForUser(
                        rs.getString("name"),
                        rs.getFloat("reviewStar"),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewMsg"))
                , userIdx);
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

    // Get User Info
    public User getUser(int userIdx){
        String getUserQuery = "SELECT U.userIdx, U.name, U.email From User U WHERE U.userIdx = ?";

        return (User) this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("email")
                )
                ,userIdx);

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

}
