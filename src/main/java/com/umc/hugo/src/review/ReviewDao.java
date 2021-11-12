package com.umc.hugo.src.review;

import com.umc.hugo.src.review.model.*;
import com.umc.hugo.src.store.Store;
import com.umc.hugo.src.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        String getUserQuery = "SELECT U.userIdx, U.name, U.password, U.email, U.profileImgUrl, U.mailFlag, U.smsFlag, U.status " +
                "From User U WHERE U.userIdx = ?";

        return (User) this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("profileImgUrl"),
                        rs.getInt("mailFlag"),
                        rs.getInt("smsFlag"),
                        rs.getString("status")
                )
                ,userIdx);

    }

    // Get Store Info
    public Store getStore(int storeIdx){
        String getStoreQuery = "SELECT S.foodIdx, S.name, S.storeImgUrl , S.storeInfoMsg, S.availableWay, S.storeStar, " +
                "S.starNum, S.reviewNum, " +
                "S.deliveryTimeMsg, S.leastPriceMsg, " +
                "substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) deliveryTipMsg ,S.status " +
                "From Store S " +
                "Where S.storeIdx = ? and S.status = 'active'";

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
                        rs.getString("deliveryTipMsg"),
                        rs.getString("status")
                )
                ,storeIdx);
    }

    //PATCH
    // 1. 리뷰 내용 수정
    public int modifyReviewMsg(PatchReviewMsgReq patchReviewMsgReq) {
        String modifyReviewMsgQuery = "update Review set reviewMsg = ? where reviewIdx = ? ";
        Object[] modifyReviewMsgParams = new Object[]{patchReviewMsgReq.getReviewMsg(), patchReviewMsgReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyReviewMsgQuery, modifyReviewMsgParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 2. 리뷰 ImgUrl 수정
    public int modifyReviewImgUrl(PatchReviewImgUrlReq patchReviewImgUrlReq) {
        String modifyReviewImgUrlQuery = "update Review set reviewImgUrl = ? where reviewIdx = ? ";
        Object[] modifyReviewImgUrlParams = new Object[]{patchReviewImgUrlReq.getReviewImgUrl(), patchReviewImgUrlReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyReviewImgUrlQuery, modifyReviewImgUrlParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 3. 리뷰 별점 수정
    public int modifyReviewStarNum(PatchReviewStarReq patchReviewStarReq) {
        String modifyReviewStarQuery = "update Review set ReviewStar = ? where reviewIdx = ? ";
        Object[] modifyReviewStarParams = new Object[]{patchReviewStarReq.getReviewStar(), patchReviewStarReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyReviewStarQuery, modifyReviewStarParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 4. 리뷰 status 수정
    public int modifyReviewStatus(PatchReviewStatusReq patchReviewStatusReq) {
        String modifyReviewStatusQuery = "update Review set status = ? where reviewIdx = ? ";
        Object[] modifyReviewStatusParams = new Object[]{patchReviewStatusReq.getStatus(), patchReviewStatusReq.getReviewIdx()};

        return this.jdbcTemplate.update(modifyReviewStatusQuery, modifyReviewStatusParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

}
