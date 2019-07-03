package com.doc.controller.Weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.UtilsTools.InternetTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.doc.controller.Weixin 于2019/4/8 由Administrator 创建 .
 */
@RestController
@Api(description = "微信的相关接口", tags = "微信的相关接口")
@RequestMapping("/cp")
public class WeixinCheck {
    @Autowired
    private InternetTools internetTools;

    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/getechostr", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "微信验证接口！", notes = "微信验证接口！")
    public String getechostr(@RequestParam String signature,
                             @RequestParam String timestamp,
                             @RequestParam String nonce,
                             @RequestParam String echostr) {
        System.out.println("加密签名：" + signature);
        System.out.println("时间戳：" + timestamp);
        System.out.println("随机数：" + nonce);
        System.out.println("随机字符串" + echostr);

        return echostr;
    }


    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/gettoken", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "微信获取token接口！", notes = "微信获取token接口！")
    public String gettoken() {
        String token = internetTools.ApiGet(
                "https://api.weixin.qq.com/cgi-bin/token?" +
                        "grant_type=client_credential&appid=wxc94f3f2f692ee6f4" +
                        "&secret=66e4b6460dd66f038d15e8ae1027a01a");
        System.out.println("获取的token：" + token);

        JSONObject tk=  JSON.parseObject(token);

        String users=internetTools.ApiGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token="
                +tk.getString("access_token"));//+"&next_openid=\"\""
        JSONObject us=  JSON.parseObject(users);
        JSONObject data=us.getJSONObject("data");
        JSONArray openids=data.getJSONArray("openid");

        //客户发送消息，POST方法
        for(Object openid:openids){
            String content="{\n" +
                    "    \"touser\":\""+openid.toString()+"\",\n" +
                    "    \"msgtype\":\"text\",\n" +
                    "    \"text\":\n" +
                    "    {\n" +
                    "         \"content\":\"测试发送微信消息，打扰了，不好意思！\"\n" +
                    "    }\n" +
                    "}";
            internetTools.ApiPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token,content);
        }

        return token;
    }


    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/sendmessage", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "微信发送消息给用户的接口！", notes = "微信发送消息给用户的接口！")
    public String sendmessage(@RequestParam String token) {

        String users=internetTools.ApiGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+token);//+"&next_openid=\"\""
        JSONObject us=  JSON.parseObject(users);
        JSONObject data=us.getJSONObject("data");
        JSONArray openids=data.getJSONArray("openid");

        //客户发送消息，POST方法
        for(Object openid:openids){
            String content="{\n" +
                    "    \"touser\":\""+openid.toString()+"\",\n" +
                    "    \"msgtype\":\"text\",\n" +
                    "    \"text\":\n" +
                    "    {\n" +
                    "         \"content\":\"测试发送微信消息，打扰了，不好意思！\"\n" +
                    "    }\n" +
                    "}";
            internetTools.ApiPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token,content);
        }

        return token+":"+openids.toJSONString();
    }
}
