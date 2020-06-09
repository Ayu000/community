package com.ayu.community.ayu.provider;

import com.alibaba.fastjson.JSON;
import com.ayu.community.ayu.dto.AccessTokenDTO;
import com.ayu.community.ayu.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset = utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try(Response response = client.newCall(request).execute()){
            String string = response.body().string();
            String tokrn = string.split("&")[0].split("=")[1];
            return tokrn;
        }catch (IOException e){

        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.github.com/user?access_tokrn="+accessToken).build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

