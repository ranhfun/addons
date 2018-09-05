package com.ranhfun.zip.util;

import java.io.BufferedInputStream;   
import java.io.File;   
import java.io.FileInputStream;   
import java.io.FileNotFoundException;
import java.io.FileOutputStream;   
import java.io.OutputStream;
import java.util.List;
import java.util.zip.CRC32;   
import java.util.zip.CheckedOutputStream;   
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
  
  
public class ZipCompressor {   
	
	static final int BUFFER = 8192;   
	  
    public void compress(String basePath, List<String> srcPathName, File zipFile) throws FileNotFoundException {
    	FileOutputStream fileOutputStream = new FileOutputStream(zipFile);   
    	compress(basePath, srcPathName, fileOutputStream);
    }
    
    public void compress(String basePath, List<String> srcPathName, OutputStream os) {   
        try {   
            CheckedOutputStream cos = new CheckedOutputStream(os,   
                    new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);   
            String basedir = "";   
            for (String path : srcPathName) {
	            File file = new File(basePath, path);   
	            compress(file, out, basedir);
            }
            out.close();   
        } catch (Exception e) {   
            throw new RuntimeException(e);   
        }   
    }   
  
    private void compress(File file, ZipOutputStream out, String basedir) {   
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {   
            this.compressDirectory(file, out, basedir);   
        } else {   
            this.compressFile(file, out, basedir);   
        }   
    }   
  
    /** 压缩一个目录 */  
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {   
        if (!dir.exists())   
            return;   
  
        File[] files = dir.listFiles();   
        for (int i = 0; i < files.length; i++) {   
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");   
        }   
    }   
  
    /** 压缩一个文件 */  
    private void compressFile(File file, ZipOutputStream out, String basedir) {   
        if (!file.exists()) {   
            return;   
        }   
        try {   
            BufferedInputStream bis = new BufferedInputStream(   
                    new FileInputStream(file));   
            ZipEntry entry = new ZipEntry(basedir + file.getName());   
            out.putNextEntry(entry);   
            int count;   
            byte data[] = new byte[BUFFER];   
            while ((count = bis.read(data, 0, BUFFER)) != -1) {   
                out.write(data, 0, count);   
            }   
            bis.close();   
        } catch (Exception e) {   
            throw new RuntimeException(e);   
        }   
    }    
} 