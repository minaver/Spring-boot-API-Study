package com.umc.hugo.src.shop;

import com.umc.hugo.src.shop.model.PostShopReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ShopDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // POST
    public int addShop(PostShopReq postShopReq){
        String createShopQuery = "insert into Shop (userIdx,menuIdx,menuNum) VALUES (?,?,?)";
        Object[] createShopParams = new Object[]{
                postShopReq.getUserIdx(),postShopReq.getMenuIdx(), postShopReq.getMenuNum()
        };
        this.jdbcTemplate.update(createShopQuery, createShopParams);

        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    // PATCH
    // increase Store orderNum
    public int increaseStoreOrderNum(PostShopReq postShopReq) {
        String increaseStoreOrderNumQuery = "update Store S " +
                                            "inner join Menu M " +
                                            "on S.storeIdx = M.storeIdx " +
                                            "set S.orderNum = S.orderNum+ 1 " +
                                            "where M.menuIdx = ? ";
        Object[] increaseStoreOrderNumParams = new Object[]{postShopReq.getMenuIdx()};

        return this.jdbcTemplate.update(increaseStoreOrderNumQuery, increaseStoreOrderNumParams);
    }
}
