package Pluto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import freemarker.template.SimpleDate;

public class uploadmusic extends ActionSupport {

	private String upload;
	private String uploadContentType;
	private String uploadFileName;
	private String savePath;
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	private String getSavePath() throws Exception {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public String execute() throws Exception {
		// 设置页面编码格式相关信息
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		
		//生成文件名
		String fileType = getUploadFileName().substring(getUploadFileName().lastIndexOf("."));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
		Date dt = new Date();
		Random rd = new Random();
		setUploadFileName(sdf.format(dt) + rd.nextInt(9999) + fileType);
		
		if ("audio/mpeg".equals(getUploadContentType())) {
			FileOutputStream fos = new FileOutputStream(getSavePath() + "\\"
					+ getUploadFileName());
			FileInputStream fis = new FileInputStream(getUpload());
			byte[] buffer = new byte[10240];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			//创建文件路径属性
			String filePath = "upload\\\\" + getUploadFileName();
			out.println(function.PlutoJump("上传成功，请认真填写歌曲相关内容", "upload.jsp?path=" + filePath));
		} else {
			out.println(function.PlutoJump("file must be mp3", "uploadmusic.jsp"));
		}
		return null;
	}
}





