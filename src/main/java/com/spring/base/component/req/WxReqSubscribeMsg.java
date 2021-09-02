package com.spring.base.component.req;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class WxReqSubscribeMsg implements Serializable {
    private static final long serialVersionUID = 8823582308865334483L;

    private String tempId;

    private String touser;
    private  cn.hutool.json.JSONObject data;


}
