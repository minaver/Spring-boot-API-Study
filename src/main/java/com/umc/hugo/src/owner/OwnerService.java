package com.umc.hugo.src.owner;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.secret.Secret;
import com.umc.hugo.src.owner.model.*;
import com.umc.hugo.util.AES128;
import com.umc.hugo.util.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.hugo.config.BaseResponseStatus.*;

@Service
public class OwnerService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final OwnerDao ownerDao;
    private final OwnerProvider ownerProvider;
    private final JwtService jwtService;


    @Autowired //readme 참고
    public OwnerService(OwnerDao ownerDao, OwnerProvider ownerProvider, JwtService jwtService) {
        this.ownerDao = ownerDao;
        this.ownerProvider = ownerProvider;
        this.jwtService = jwtService;
    }
    // ******************************************************************************
    // 회원가입(POST)
    public PostOwnerRes createOwner(PostOwnerReq postOwnerReq) throws BaseException {
        // 중복 확인: 해당 이메일을 가진 유저가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        if (ownerProvider.checkEmail(postOwnerReq.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try {
            // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
            // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postOwnerReq.getPassword()); // 암호화코드
            postOwnerReq.setPassword(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int ownerIdx = ownerDao.createOwner(postOwnerReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(ownerIdx);
            return new PostOwnerRes(jwt,ownerIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원정보 수정(Patch)
    // 회원 이름 수정
    public void modifyOwnerName(PatchOwnerReq patchOwnerReq) throws BaseException {
        try {
            int result = ownerDao.modifyOwnerName(patchOwnerReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 회원 비밀번호 수정
    public void modifyOwnerPassword(PatchOwnerPasswordReq patchOwnerPasswordReq) throws BaseException {
        Owner owner = ownerDao.getPwdByOwnerIdx(patchOwnerPasswordReq);
        String nowPwd;
        String newPwd;
        // 1. 사용자로 부터 받은 현재 비밀번호(nowPassword) 암호화 부분
        try {
            nowPwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(owner.getPassword()); // 암호화
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 떄문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        // 현재 비말번호(nowPassword)가 일치한다면 newPwd를 Patch 해준다.
        if (patchOwnerPasswordReq.getNowPassword().equals(nowPwd)) {
            try {
                // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
                // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
                newPwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchOwnerPasswordReq.getNewPassword()); // 암호화코드
                patchOwnerPasswordReq.setNewPassword(newPwd);
            } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }
            try {
                int result = ownerDao.modifyOwnerPassword(patchOwnerPasswordReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
                if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                    throw new BaseException(MODIFY_FAIL_USERNAME);
                }
            } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
                throw new BaseException(DATABASE_ERROR);
            }

        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(PATCH_NOW_PASSWORD_WRONG);
        }

    }
    // 회원 상태 수정
    public void modifyOwnerStatus(PatchOwnerStatusReq patchOwnerStatusReq) throws BaseException {
        try {
            int result = ownerDao.modifyOwnerStatus(patchOwnerStatusReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
