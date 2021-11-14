package com.umc.hugo.src.store;

import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.store.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // GET
    public List<GetStoreRes> storeResByStar(int foodIdx){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar," +
                        "S.starNum, ifnull(shortMenu.shortMenuMsg,0) as shortMenuMsg, " +
                        "S.leastPriceMsg, substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) as deliveryTipMsg, S.deliveryTimeMsg " +
                        "FROM Store S " +
                        "LEFT JOIN (SELECT storeIdx, GROUP_CONCAT(name) as shortMenuMsg " +
                        "            FROM Menu " +
                        "            GROUP BY storeIdx) shortMenu " +
                        "ON S.storeIdx = shortMenu.storeIdx " +
                        "WHERE foodIdx = ? " +
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
        , foodIdx);
    }
    public List<GetStoreRes> storeResByReview(int foodIdx){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar," +
                        "S.starNum, ifnull(shortMenu.shortMenuMsg,0) as shortMenuMsg, " +
                        "S.leastPriceMsg, substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) as deliveryTipMsg, S.deliveryTimeMsg " +
                        "FROM Store S " +
                        "LEFT JOIN (SELECT storeIdx, GROUP_CONCAT(name) as shortMenuMsg " +
                        "            FROM Menu " +
                        "            GROUP BY storeIdx) shortMenu " +
                        "ON S.storeIdx = shortMenu.storeIdx " +
                        "WHERE foodIdx = ? " +
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
        , foodIdx);
    }
    public List<GetStoreRes> storeResByIdx(int foodIdx){

        return this.jdbcTemplate.query("SELECT S.storeImgUrl , S.name, S.availableWay, S.storeStar," +
                        "S.starNum, ifnull(shortMenu.shortMenuMsg,0) as shortMenuMsg, " +
                        "S.leastPriceMsg, substr(S.deliveryTipMsg,1,instr(S.deliveryTipMsg,'~')) as deliveryTipMsg, S.deliveryTimeMsg " +
                        "FROM Store S " +
                        "LEFT JOIN (SELECT storeIdx, GROUP_CONCAT(name) as shortMenuMsg " +
                        "            FROM Menu " +
                        "            GROUP BY storeIdx) shortMenu " +
                        "ON S.storeIdx = shortMenu.storeIdx " +
                        "WHERE foodIdx = ? " +
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
        , foodIdx);
    }

    // INNER FUNCTION
    public Food getFood(int foodIdx){
        String getFoodQuery = "SELECT name , foodImgUrl, status from Food where foodIdx = ?";

        return (Food) this.jdbcTemplate.queryForObject(getFoodQuery,
                (rs, rowNum) -> new Food(
                        rs.getString("name"),
                        rs.getString("foodImgUrl"),
                        rs.getString("status")
                )
        ,foodIdx);
    }

    public Store getStore(int storeIdx){
        String getStoreQuery = "SELECT foodIdx, name, storeImgUrl, storeInfoMsg, availableWay, storeStar, starNum, reviewNum," +
                "deliveryTimeMsg, leastPriceMsg, deliveryTipMsg, status " +
                "From Store " +
                "Where storeIdx = ?";

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
                ),storeIdx);
    }

    // POST
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

    //PATCH
    // 1. 식당 이름 수정
    public int modifyStoreName(PatchStoreReq patchStoreReq) {
        String modifyStoreNameQuery = "update Store set name = ? where storeIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyStoreNameParams = new Object[]{patchStoreReq.getName(), patchStoreReq.getStoreIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyStoreNameQuery, modifyStoreNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 2. 식당 ImgUrl 수정
    public int modifyStoreImgUrl(PatchStoreImgUrlReq patchStoreImgUrlReq) {
        String modifyStoreImgUrlQuery = "update Store set storeImgUrl = ? where storeIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyStoreImgUrlParams = new Object[]{patchStoreImgUrlReq.getStoreImgUrl(), patchStoreImgUrlReq.getStoreIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyStoreImgUrlQuery, modifyStoreImgUrlParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 3. 식당 status 수정
    public int modifyStoreStatus(PatchStoreStatusReq patchStoreStatusReq) {
        String modifyStoreStatusQuery = "update Store set status = ? where storeIdx = ? ";
        Object[] modifyStoreStatusParams = new Object[]{patchStoreStatusReq.getStatus(), patchStoreStatusReq.getStoreIdx()};

        return this.jdbcTemplate.update(modifyStoreStatusQuery, modifyStoreStatusParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }


}
