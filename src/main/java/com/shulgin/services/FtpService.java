package com.shulgin.services;

public interface FtpService {
    void auth(String username, String password) throws Exception;

    void downloadFile(String path) throws Exception;

    void uploadFile(String path) throws Exception;
}
