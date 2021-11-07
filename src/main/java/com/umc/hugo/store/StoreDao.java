package com.umc.hugo.store;

import com.umc.hugo.store.model.GetStoreRes;
import com.umc.hugo.store.model.PostStoreReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class StoreDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetStoreRes> storeResByStar(){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar," +
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

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar," +
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

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar," +
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

    public int addStore(PostStoreReq poststoreReq){
        String createUserQuery = "insert into Baemin.Store (foodIdx,name,storeImgUrl,storeInfoMsg,availableWay, " +
                "storeStar,starNum,reviewNum,deliveryTimeMsg,leastPriceMsg,deliveryTipMsg) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                poststoreReq.getFoodIdx(), poststoreReq.getName(),poststoreReq.getStoreImgUrl(),poststoreReq.getStoreInfoMsg(),poststoreReq.getAvailableWay(),
                poststoreReq.getStoreStar(),poststoreReq.getStarNum(),poststoreReq.getReviewNum(),poststoreReq.getDeliveryTimeMsg(),
                poststoreReq.getLeastPriceMsg(),poststoreReq.getDeliveryTipMsg()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

}
