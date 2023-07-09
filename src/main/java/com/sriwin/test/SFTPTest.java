package com.sriwin.test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.sriwin.model.FTPModel;
import com.sriwin.constants.FTPConstants;

import java.io.File;

public class SFTPTest {

  public static void main(String[] args) {
    try {
      // this program is not exiting properly, taking long time
      //
      FTPModel ftpModel = FTPModel.builder()
          .ftpUserName(FTPConstants.FTP_USER_NAME)
          .ftpPassword(FTPConstants.FTP_PASSWORD)
          .remoteDir(FTPConstants.REMOTE_DIR)
          .localDir(FTPConstants.LOCAL_DIR)
          .fileName(FTPConstants.FILE_NAME)
          .ftpHost(FTPConstants.FTP_HOST)
          .build();

      //
      uploadFile(ftpModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static ChannelSftp connect2RemoteHost(FTPModel ftpModel)
      throws JSchException {
    System.out.println("connect2RemoteHost => Start");
    java.util.Properties config = new java.util.Properties();
    config.put("StrictHostKeyChecking", "no");

    JSch jsch = new JSch();
    //jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
    Session jschSession = jsch.getSession(ftpModel.getFtpUserName(), ftpModel.getFtpHost());
    jschSession.setConfig(config);

    jschSession.setPassword(ftpModel.getFtpPassword());
    jschSession.connect();
    ChannelSftp channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
    System.out.println("connect2RemoteHost => end => " + channelSftp);
    return channelSftp;
  }

  public static void uploadFile(FTPModel ftpModel) throws JSchException, SftpException {
    ChannelSftp channelSftp = connect2RemoteHost(ftpModel);
    channelSftp.connect();

    String sourceFile = ftpModel.getLocalDir() + File.separator + ftpModel.getFileName();
    String targetFile = ftpModel.getRemoteDir() + File.separator + ftpModel.getFileName();
    channelSftp.put(sourceFile, targetFile);

    channelSftp.exit();
  }

  public static void downloadFile(FTPModel ftpModel) throws JSchException, SftpException {
    ChannelSftp channelSftp = connect2RemoteHost(ftpModel);
    channelSftp.connect();

    String sourceFile = ftpModel.getLocalDir() + File.separator + ftpModel.getFileName();
    String targetFile = ftpModel.getRemoteDir() + File.separator + ftpModel.getFileName();
    channelSftp.get(targetFile, sourceFile);

    channelSftp.exit();
  }
}