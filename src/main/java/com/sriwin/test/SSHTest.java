package com.sriwin.test;

import com.sriwin.constants.FTPConstants;
import com.sriwin.model.FTPModel;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.File;
import java.io.IOException;

public class SSHTest {
  public static void main(String[] args) {
    try {
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

  private static SSHClient connect2RemoteHost(FTPModel ftpModel) throws IOException {
    SSHClient client = new SSHClient();
    client.addHostKeyVerifier(new PromiscuousVerifier());
    client.connect(ftpModel.getFtpHost());
    client.authPassword(ftpModel.getFtpUserName(), ftpModel.getFtpPassword());
    return client;
  }

  public static void uploadFile(FTPModel ftpModel) throws IOException {
    SSHClient sshClient = connect2RemoteHost(ftpModel);
    SFTPClient sftpClient = sshClient.newSFTPClient();

    String sourceFile = ftpModel.getLocalDir() + File.separator + ftpModel.getFileName();
    String targetFile = ftpModel.getRemoteDir() + ftpModel.getFileName();
    sftpClient.put(sourceFile, targetFile);

    sftpClient.close();
    sshClient.disconnect();
  }

  public void downloadFile(FTPModel ftpModel) throws IOException {
    SSHClient sshClient = connect2RemoteHost(ftpModel);
    SFTPClient sftpClient = sshClient.newSFTPClient();

    String sourceFile = ftpModel.getLocalDir() + File.separator + ftpModel.getFileName();
    String targetFile = ftpModel.getRemoteDir() + File.separator + ftpModel.getFileName();
    sftpClient.get(targetFile, sourceFile);

    sftpClient.close();
    sshClient.disconnect();
  }
}
