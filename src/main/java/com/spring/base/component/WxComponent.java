package com.spring.base.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.spring.base.component.req.ReqAuthPhoneNumber;
import com.spring.base.component.req.WxReqSubscribeMsg;
import com.spring.base.utils.Constant;
import com.spring.base.utils.RedisUtil;
import com.spring.base.utils.aes.AesEncryptUtils;
import com.spring.base.utils.errorcode.ServiceError;
import com.spring.base.utils.exception.WeiDianServiceException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;


/**
 * 功能描述: <br>
 * https://blog.csdn.net/w410589502/article/details/77702358
 * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
 * @Param:
 * @Return:
 * @Author: chengranran
 * @Date: 2020/11/12 17:56
 */
@Component
public class WxComponent {
    public static final Logger logger = LoggerFactory.getLogger(WxComponent.class);
    @Autowired
    private RedisUtil redisUtil;


    @Qualifier("restTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${weixin.appid}")
    String appId;

    @Value("${weixin.secret}")
    String secret;
    @Value("${weixin.api.gettoken}")
    String getTokenPrefix;


    @Value("${weixin.api.createwxaqrcode}")
    String createWxQrcodePrefix;

    @Value("${weixin.api.getSessionKey}")
    String getSessionKey;



    @Value("${nfs.save}")
    String nfsSave;


    //获取token
    public String getAccessToken() {
        String url = getTokenPrefix + "?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
        JSONObject jsonObject = new JSONObject();

        String requestBodyString = JSON.toJSONString(jsonObject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBodyString, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        logger.info("the result of getToken:" + responseEntity);
        if (responseEntity.getStatusCode().value() == 200) {
            String responseString = responseEntity.getBody();
            JSONObject result = JSONObject.parseObject(responseString);
            String access_token = result.getString("access_token");
            redisUtil.set(Constant.WEIXIN_TOEKN_PREFIX+"access_token", access_token, Integer.parseInt(result.getString("expires_in")));
            return access_token;
        }

        return "";
    }

    //获取token
    public String getSessionKey(String jsCode) {
        String url = getSessionKey + "?grant_type=authorization_code&appid=" + appId + "&secret=" + secret+"&js_code="+jsCode;
        JSONObject jsonObject = new JSONObject();

        String requestBodyString = JSON.toJSONString(jsonObject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBodyString, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        logger.info("the result of getSessionKey:" + responseEntity);
        String session_key="";
        if (responseEntity.getStatusCode().value() == 200) {
            String responseString = responseEntity.getBody();
            JSONObject result = JSONObject.parseObject(responseString);
            if(result.containsKey("errcode")){
                /*{"errcode":40029,"errmsg":"invalid code, hints: [ req_id: 4GhF8Kqge-DDQreA ]"}*/
                throw new WeiDianServiceException(ServiceError.CONNECT_MINI_PROGRAM_FAILURE_CODE, ServiceError.CONNECT_MINI_PROGRAM_FAILURE_MSG);

            }else{
                session_key= result.getString("session_key");

            }

        }

        return session_key;
    }

    //获取token
    /**
     * 功能描述: <br>
     * 〈〉
     * @Param:
     * @Return: {"phoneNumber":"13764965754","purePhoneNumber":"13764965754","countryCode":"86","watermark":{"timestamp":1608013914,"appid":"wxddc0c0e3ee0a48a2"}}
     * @Author: chengranran
     * @Date: 2020/12/15 14:55
     */
    public String DecodeAuthPhone(ReqAuthPhoneNumber req) {
        if(req.getSessionKey()==null||"".equals(req.getSessionKey())){
            req.setSessionKey(getSessionKey(req.getLoginCode()));
        }

        //调用加密算法
        String phone="";
        String result=  AesEncryptUtils.decrypt( req.getEncryptedData(), req.getSessionKey(), req.getIv());
        if(!"".equals(result)){
            JSONObject jsonObject = JSONObject.parseObject(result);
            phone = jsonObject.getString("phoneNumber");
        }
        return  phone;
    }


    //获取token
    public String createWxQrcode(String wxTargetPath,String prefix ,String name,String scene) {
        String savePath=nfsSave+prefix+"/"+name;

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String access_token = (String) redisUtil.get(Constant.WEIXIN_TOEKN_PREFIX+"access_token");
            logger.info("the value of redis is {}}",access_token);
            if ("".equals(access_token) || access_token == null) {
                logger.info("the value of redis is null");
                access_token = getAccessToken();
                if("".equals(access_token)){
                    throw new WeiDianServiceException(ServiceError.CONNECT_MINI_PROGRAM_FAILURE_CODE, ServiceError.CONNECT_MINI_PROGRAM_FAILURE_MSG);
                }
            }
            String url = createWxQrcodePrefix + "?access_token=" + access_token;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", wxTargetPath);
            if(wxTargetPath==null||"".equals(wxTargetPath)){
                jsonObject.put("page", "pages/index/index");
            }

            jsonObject.put("width", 280);
            jsonObject.put("scene",scene);
            jsonObject.put("is_hyaline",true);

            String requestBodyString = JSON.toJSONString(jsonObject);

            restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBodyString, headers);
            URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();

            ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(uri,httpEntity, byte[].class);
            logger.info("调用小程序生成微信永久二维码URL接口返回结果:" + responseEntity);
            /**
             width	number	430	否	二维码的宽度，单位 px，最小 280px，最大 1280px
             */
            logger.info("调用小程序生成微信永久二维码URL接口返回结果:" + responseEntity.getBody());
            if (responseEntity.getStatusCode().value() == 200) {

                byte[] result = responseEntity.getBody();
                logger.info(Base64.encodeBase64String(result));
                inputStream = new ByteArrayInputStream(result);

                File file = new File(savePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                outputStream = new FileOutputStream(savePath);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.flush();

            }
        } catch (Exception e) {
            logger.error("调用小程序生成微信永久小程序码URL接口异常:{}",e.getMessage() ,e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return "";
    }

    public static void main(String[] args) {
        String url = "https://api.weixin.qq.com/cgi-bin/token/" + "?grant_type=client_credential&appid=" + "wxddc0c0e3ee0a48a2" + "&secret=" + "26fc928c160613aece2ea0f808b7cbdf";
        JSONObject jsonObject = new JSONObject();

        String requestBodyString = JSON.toJSONString(jsonObject);
        RestTemplate restTemplate1=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBodyString, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(true).toUri();
        ResponseEntity<String> responseEntity = restTemplate1.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        logger.info("the result of getToken:" + responseEntity);
        if (responseEntity.getStatusCode().value() == 200) {
            String responseString = responseEntity.getBody();
            JSONObject result = JSONObject.parseObject(responseString);
            String access_token = result.getString("access_token");

        }

    }


}
