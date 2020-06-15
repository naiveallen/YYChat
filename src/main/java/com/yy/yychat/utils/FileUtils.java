package com.yy.yychat.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class FileUtils {

	public static File createFileByUrl(String url, String suffix) {
		byte[] byteFile = getImageFromNetByUrl(url);
		if (byteFile != null) {
			File file = getFileFromBytes(byteFile, suffix);
			return file;
		} else {
			return null;
		}
	}

	private static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			byte[] btImg = readInputStream(inStream);
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

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
