package com.sriwin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FTPModel {
  private String ftpUserName;
  private String ftpPassword;
  private String ftpHost;
  private String localDir;
  private String remoteDir;
  private String fileName;
}