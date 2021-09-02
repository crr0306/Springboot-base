package com.spring.base.component.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ReqAuthPhoneNumber implements Serializable {
    private static final long serialVersionUID = 8823582308865334483L;
    @ApiModelProperty(value = "客户openid")
    private String openId;
    @ApiModelProperty(value = "授权加密信息")
    private String encryptedData;
    @ApiModelProperty(value = "授权加密信息密钥")
    private String iv;
    @ApiModelProperty(value = "客户登录临时凭证")
    private String loginCode;
    @ApiModelProperty(value = "客户会话")
    private String sessionKey;


}
