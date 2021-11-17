package com.umc.hugo.src.owner;

import com.umc.hugo.src.owner.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class OwnerDao {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************


    // 회원가입
    public int createOwner(PostOwnerReq postOwnerReq) {
        String createOwnerQuery = "insert into Owner (name, email, password, profileImgUrl) VALUES (?,?,?)"; // 실행될 동적 쿼리문
        Object[] createOwnerParams = new Object[]{postOwnerReq.getName(), postOwnerReq.getEmail(), postOwnerReq.getPassword(), postOwnerReq.getProfileImgUrl()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createOwnerQuery, createOwnerParams);
        // email -> postUserReq.getEmail(), password -> postUserReq.getPassword(), nickname -> postUserReq.getNickname() 로 매핑(대응)시킨다음 쿼리문을 실행한다.
        // 즉 DB의 User Table에 (email, password, nickname)값을 가지는 유저 데이터를 삽입(생성)한다.

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

    // 이메일 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from Owner where email = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        String checkEmailParams = email; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 회원정보 변경
    public int modifyOwnerName(PatchOwnerReq patchOwnerReq) {
        String modifyOwnerNameQuery = "update Owner set name = ? where ownerIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyOwnerNameParams = new Object[]{patchOwnerReq.getName(), patchOwnerReq.getOwnerIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyOwnerNameQuery, modifyOwnerNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public int modifyOwnerPassword(PatchOwnerPasswordReq patchOwnerPasswordReq) {
        String modifyOwnerPasswordQuery = "update Owner set password = ? where ownerIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 password으로 변경한다.
        Object[] modifyOwnerPasswordParams = new Object[]{patchOwnerPasswordReq.getNewPassword(), patchOwnerPasswordReq.getOwnerIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyOwnerPasswordQuery, modifyOwnerPasswordParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public int modifyOwnerStatus(PatchOwnerStatusReq patchOwnerStatusReq) {
        String modifyOwnerStatusQuery = "update Owner set status = ? where ownerIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 password으로 변경한다.
        Object[] modifyOwnerStatusParams = new Object[]{patchOwnerStatusReq.getStatus(), patchOwnerStatusReq.getOwnerIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyOwnerStatusQuery, modifyOwnerStatusParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }


    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public Owner getPwdByEmail(PostLoginReq postLoginReq) {
        String getPwdQuery = "select ownerIdx, name, email, password, profileImgUrl,status from Owner where email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postLoginReq.getEmail(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new Owner(
                        rs.getInt("ownerIdx"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("profileImgUrl"),
                        rs.getString("status")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public Owner getPwdByOwnerIdx(PatchOwnerPasswordReq patchOwnerPasswordReq) {
        String getPwdQuery = "select ownerIdx, name, email, password, profileImgUrl, status from Owner where ownerIdx = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getPwdParams = patchOwnerPasswordReq.getOwnerIdx(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new Owner(
                        rs.getInt("ownerIdx"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("profileImgUrl"),
                        rs.getString("status")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // User 테이블에 존재하는 전체 유저들의 정보 조회
    public List<GetOwnerRes> getOwners() {
        String getOwnersQuery = "select * from Owner"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getOwnersQuery,
                (rs, rowNum) -> new GetOwnerRes(
                        rs.getInt("ownerIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profileImgUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }

    // 해당 nickname 갖는 유저들의 정보 조회
    public List<GetOwnerRes> getOwnersByNickname(String nickname) {
        String getOwnersByNicknameQuery = "select * from Owner where name =?"; // 해당 name을 만족하는 유저를 조회하는 쿼리문
        String getOwnersByNicknameParams = nickname;
        return this.jdbcTemplate.query(getOwnersByNicknameQuery,
                (rs, rowNum) -> new GetOwnerRes(
                        rs.getInt("ownerIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profileImgUrl")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getOwnersByNicknameParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 해당 userIdx를 갖는 유저조회
    public GetOwnerRes getOwner(int ownerIdx) {
        String getOwnerQuery = "select * from Owner where ownerIdx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getOwnerParams = ownerIdx;
        return this.jdbcTemplate.queryForObject(getOwnerQuery,
                (rs, rowNum) -> new GetOwnerRes(
                        rs.getInt("ownerIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profileImgUrl")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getOwnerParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}
