package com.ranhfun.resteasy.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="version")
@XmlAccessorType(XmlAccessType.FIELD)
public class VersionApi {
	@XmlElement
	private int vCode=1;
	@XmlElement
	private String vName="1.0.0";
	@XmlElement
	//private String vContent="发现新版本，建议更新.是否现在升级？";
	private String vContent="建议更新.是否现在升级？";
	@XmlElement
	private String vApp="http://www.fjpicc.com/p/assets/0.0.6/ctx/ext/mobile/renbao.apk";
	@XmlElement
	private Boolean vFlag=Boolean.FALSE;
	
	public VersionApi() {}
	
	public int getvCode() {
		return vCode;
	}
	public void setvCode(int vCode) {
		this.vCode = vCode;
	}
	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}
	public String getvContent() {
		return vContent;
	}
	public void setvContent(String vContent) {
		this.vContent = vContent;
	}
	public String getvApp() {
		return vApp;
	}
	public void setvApp(String vApp) {
		this.vApp = vApp;
	}

	public Boolean getvFlag() {
		return vFlag;
	}

	public void setvFlag(Boolean vFlag) {
		this.vFlag = vFlag;
	}
}
