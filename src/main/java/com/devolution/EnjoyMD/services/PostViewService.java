package com.devolution.EnjoyMD.services;

public interface PostViewService {
    int addView(Integer postId, Integer userId, String ipAddress);
    int getViewCount(Integer postId);
}
