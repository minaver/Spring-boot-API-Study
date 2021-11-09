package com.umc.hugo.review;

import com.umc.hugo.review.model.GetReviewRes;
import com.umc.hugo.review.model.PostReviewReq;
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
