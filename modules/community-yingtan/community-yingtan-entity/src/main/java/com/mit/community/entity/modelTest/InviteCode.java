package com.mit.community.entity.modelTest;

import lombok.Data;

/**
 * 邀请码
 *
 * @author Mr.Deng
 * @date 2018/11/14 11:01
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class InviteCode {

    private String modifytime;
    private String dataStatus;
    private String inviteCode;
    private String useTimes;
    private String inviteCodeType;
    private String deviceGroupId;
}
