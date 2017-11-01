package com.example.mvvmde;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/10/31.
 */

public class UserEntity {
    private String username;
    private String nickname;
    private int age;

    //绑定ImageView
    private String userface;

    public UserEntity() {
    }

    @BindingAdapter("bind:userface")
    public static void getInternetImage(ImageView iv, String userface) {
        Glide.with(iv.getContext()).load(userface).into(iv);
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserEntity(int age, String nickname, String username) {
        this.age = age;
        this.nickname = nickname;
        this.username = username;
    }

    public UserEntity(String username, String nickname, int age, String userface) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
        this.userface = userface;
    }
}
