package com.umc.hugo.menu;

import com.umc.hugo.menu.model.GetMenuRes;
import com.umc.hugo.menu.model.PostMenuReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class MenuDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
