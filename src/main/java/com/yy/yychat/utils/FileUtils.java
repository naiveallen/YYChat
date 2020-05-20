package com.yy.yychat.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;


@Service
public class FileUtils {
	/**
	 * 根据url拿取file
	 * 
	 * @param url
	 * @param suffix
	 *            文件后缀名
	 */
	public static File createFileByUrl(String url, String suffix) {
		byte[] byteFile = getImageFromNetByUrl(url);
		if (byteFile != null) {
			File file = getFileFromBytes(byteFile, suffix);
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl
	 *            网络连接地址
	 * @return
	 */
	private static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	private static File getFileFromBytes(byte[] b, String suffix) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = File.createTempFile("pattern", "." + suffix);
			System.out.println("临时文件位置：" + file.getCanonicalPath());
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

//	public static MultipartFile createImg(String url) {
//		try {
//			// File转换成MutipartFile
//			File file = FileUtils.createFileByUrl(url, "jpg");
//			FileInputStream inputStream = new FileInputStream(file);
//			MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
//			return multipartFile;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

//	public static MultipartFile fileToMultipart(String filePath) {
//		try {
//			// File转换成MutipartFile
//			File file = new File(filePath);
//			FileInputStream inputStream = new FileInputStream(file);
//			MultipartFile multipartFile = new MockMultipartFile(file.getName(), "png", "image/png", inputStream);
//			return multipartFile;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}


	public static boolean base64ToFile(String filePath, String base64Data)  throws Exception {
		String dataPrix = "";
        String data = "";
		filePath = filePath + ".png";
        
        if(base64Data == null || "".equals(base64Data)){
            return false;
        }else{
            String [] d = base64Data.split("base64,");
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else{
                return false;
            }
        }

        byte[] bs = Base64Utils.decodeFromString(data);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(filePath), bs);
        
        return true;
	}


	public static void getThumbnail(String filePath, String fileName) {
		String original = filePath + fileName + ".png";
		String thumbnailPath = filePath + fileName + "_80x80.png";

        try {
			Thumbnails.of(original)
					.size(80, 80)
					.toFile(thumbnailPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    public static void main(String[] args) {
        getThumbnail("D:\\yychat\\avatars\\", "1.png");
    }

}
