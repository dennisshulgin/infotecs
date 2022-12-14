package com.shulgin.services;

import com.shulgin.entity.User;

public interface FtpService {
    void downloadFile(User user, String path) throws Exception;

    void uploadFile(User user, String path) throws Exception;
}
