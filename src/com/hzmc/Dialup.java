package com.hzmc;


public class Dialup {
	public String dp(String ip){
		boolean isAno = true;
        String host = "127.0.0.1";
        //String osuser = GetUserComputerInfo.getOsUsers();
        String iplist = "254.254.2.1";
        String maclist = "";
        //String cert = GetUserComputerInfo.getCert();
        String hwcode = "";
        String hwtype = "HARD";
        String username = "";
        StringBuilder buffer = new StringBuilder();
        buffer.append("EUSER:").append(username)
              .append("@@@@CERT:").append(username)
              .append("@@@@HWCODE:").append(hwcode)
              .append("@@@@HWTYPE:").append(hwtype)
              .append("@@@@IP_ADDRESS:").append(iplist)
              .append("@@@@HOST:").append(host)
              .append("@@@@MAC_ADDRESS:").append(maclist)
              .append("@@@@APPNAME:").append("demo")
              .append("@@@@CLIENT_PROCESS:").append("8888888")
              .append("@@@@VALIDATE_TIME:2099-12-31 23:59:59")
              .append("@@@@VALIDATE_TIMES:99999")
              .append("@@@@CMDTYPE:CERT_LOGON")
              .append("@@@@WHAT:HZMC-SEC-CII")
              .append("@@@@ACTION_LEVEL:0")
              .append("@@@@AUDIT_LEVEL:1");
        String content = buffer.toString();
        String crcValue = CRC16.getCrcValue(content);
        int len = content.length();
        String cmd = "";
        if(len>=1000){
            cmd = "0001-" + String.valueOf(content.length()) + "-" + crcValue + "-" + content;
        }
        else{
            cmd = "0001-0" + String.valueOf(content.length()) + "-" + crcValue + "-" + content;
        }
        return cmd;
	}
	
	public String exit(){
		String cmd = "0002-" + "8888888";
        return cmd;
	}
}
