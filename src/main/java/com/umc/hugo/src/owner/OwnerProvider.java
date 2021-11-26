package com.umc.hugo.src.owner;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.secret.Secret;
import com.umc.hugo.src.owner.model.GetOwnerRes;
import com.umc.hugo.src.owner.model.PostLoginReq;
import com.umc.hugo.src.owner.model.PostLoginRes;
import com.umc.hugo.util.AES128;
import com.umc.hugo.util.JwtServiceOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.*;

@Service
public class OwnerProvider {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final OwnerDao ownerDao;
    private final JwtServiceOwner jwtServiceOwner;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public OwnerProvider(OwnerDao ownerDao, JwtServiceOwner jwtServiceOwner) {
        this.ownerDao = ownerDao;
        this.jwtServiceOwner = jwtServiceOwner;
    }
    // ******************************************************************************


    // 로그인(password 검사)
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        Owner owner = ownerDao.getPwdByEmail(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(owner.getPassword()); // 암호화
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 떄문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (postLoginReq.getPassword().equals(password)) { //비말번호가 일치한다면 ownerIdx를 가져온다.
            int ownerIdx = ownerDao.getPwdByEmail(postLoginReq).getOwnerIdx();
            String jwt = jwtServiceOwner.createJwt(ownerIdx);
            return new PostLoginRes(ownerIdx,jwt);

        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    // 해당 이메일이 이미 Owner Table에 존재하는지 확인
    public int checkEmail(String email) throws BaseException {
        try {
            return ownerDao.checkEmail(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // User들의 정보를 조회
    public List<GetOwnerRes> getOwners() throws BaseException {
        try {
            List<GetOwnerRes> getUserRes = ownerDao.getOwners();
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 nickname을 갖는 User들의 정보 조회
    public List<GetOwnerRes> getOwnersByNickname(String nickname) throws BaseException {
        try {
            List<GetOwnerRes> getOwnerRes = ownerDao.getOwnersByNickname(nickname);
            return getOwnerRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 해당 userIdx를 갖는 User의 정보 조회
    public GetOwnerRes getOwner(int ownerIdx) throws BaseException {
        try {
            GetOwnerRes getOwnerRes = ownerDao.getOwner(ownerIdx);
            return getOwnerRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
