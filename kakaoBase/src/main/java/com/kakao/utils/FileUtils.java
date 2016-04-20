package com.kakao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Locale;

/**
 * @author baseplatform
 * @version V1.0
 * @Description:文件处理通用类
 * @date 16-4-20 13:54
 * @copyright www.tops001.com
 */
public class FileUtils {
	/**
	 * 确认filename是否有后缀名，如没有后缀名则增加使用defaultExtension为文件的后缀名
	 *
	 * @param filename
	 * @param defaultExtension
	 * @return 得到有文件后缀的文件名
	 */
	public static String checkFileExtension(String filename, String defaultExtension) {
		if (filename != null && !filename.contains(".")) {
			return filename + defaultExtension;
		}
		else {
			return filename;
		}
	}

	/**
	 * 通过url来获取Mime类型
	 *
	 * @param url
	 * @return url的Mime类型
	 */
	public static String getMimeTypeFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }

	    String type = null;
	    String extension = MimeTypeMap.getFileExtensionFromUrl(url.toLowerCase());
	    if (extension != null) {
	        MimeTypeMap mime = MimeTypeMap.getSingleton();
	        type = mime.getMimeTypeFromExtension(extension);
	    }

	    return type;
	}

	/**
	 * 通过filePath来获取Mime类型
	 *
	 * @param filePath
	 * @return url的Mime类型
	 */
    public static String getMimeType(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        String type = null;
	    String extension = getExtensionName(filePath.toLowerCase());
	    if (extension != null) {
	        MimeTypeMap mime = MimeTypeMap.getSingleton();
	        type = mime.getMimeTypeFromExtension(extension);
	    }
	    
	    return type;
	}

	/**
	 * 获取文件扩展名
	 *
	 * @param filename
	 * @return filename的扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return "";
	}

	/**
	 * 获取不带扩展名的文件名
	 *
	 * @param filename
	 * @return filename的不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 通过文件的路径获取文件名
	 *
	 * @param filepath
	 * @return 单独的文件名
	 */
	public static String getFileNameFromPath(String filepath) {
		if ((filepath != null) && (filepath.length() > 0)) {
			int sep = filepath.lastIndexOf('/');
			if ((sep > -1) && (sep < filepath.length() - 1)) {
				return filepath.substring(sep + 1);
			}
		}
		return filepath;
	}

	/**
	 * 通过文件的路径获取文件所在的目录
	 *
	 * @param filepath
	 * @return 文件所在目录
	 */
	public static String getDirectFromPath(String filepath) {
		if (!TextUtils.isEmpty(filepath)) {
			int sep = filepath.lastIndexOf('/');
			if ((sep > -1) && (sep < filepath.length() - 1)) {
				return filepath.substring(0, sep);
			}
		}
		return filepath;
	}

	/**
	 * 通过关键字来搜索相应目录下的文件
	 *
	 * @param dirName
	 * @param key
	 * @return dirName表示的目录下文件名包含key的文件名数值，如果未搜索到，返回null
	 */
	public static String[] searchFiles(String dirName, final String key) {
		// check
		if (TextUtils.isEmpty(dirName) || TextUtils.isEmpty(key)) {
			return null;
		}
		
		// exists
		File dir = new File(dirName);
		if (!dir.exists()) {
			return null;
		}
		
		// list
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String fileName) {
				return fileName.contains(key);
			}
		});
		
		// check
		if (files == null) {
			return null;
		}
		
		// concatenate
		String[] filePaths = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			filePaths[i] = files[i].getAbsolutePath();
		}
		
		return filePaths;
	}
    
    private static final String[] imageExts = new String[] {
    	"jpg",
    	"jpeg",
    	"png",
    	"bmp",
    };

	/**
	 * 判断文件是否为图片类型
	 *
	 * @param pathName
	 * @return ture:是图片类型，false:不是图片类型
	 */
	public static boolean isImageFile(String pathName) {
		if (TextUtils.isEmpty(pathName)) {
			return false;
		}
		
		// last dot
		int index = pathName.lastIndexOf(".");
		if (index == -1) {
			return false;
		}
		
		String ext = pathName.substring(index + 1);
		for (String imageExt : imageExts) {
			if (imageExt.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		
		return false;
	}


    public enum SizeUnit {
        Byte,
        KB,
        MB,
        GB,
        TB,
        Auto,
    }

	/**
	 * 根据文件的字节大小，返回人们易识别的表示，Byte, KB, MB, GB, TB等
	 *
	 * @param size
	 * @return 自动根据文件的大小，来选择合适的单位来表示文件的大小
	 */
    public static String formatFileSize(long size) {
        return formatFileSize(size, SizeUnit.Auto);
    }

	/**
	 * 根据文件的字节大小，返回人们易识别的表示，Byte, KB, MB, GB, TB等
	 *
	 * @param size
	 * @param unit
	 * @return 使用unit单位来表示文件的大小
	 */
    public static String formatFileSize(long size, SizeUnit unit) {
		if (size < 0) {
			return null;
		}

		final double KB = 1024;
		final double MB = KB * 1024;
		final double GB = MB * 1024;
		final double TB = GB * 1024;
		if (unit == SizeUnit.Auto) {
			if (size < KB) {
				unit = SizeUnit.Byte;
			} else if (size < MB) {
				unit = SizeUnit.KB;
			} else if (size < GB) {
				unit = SizeUnit.MB;
			} else if (size < TB) {
				unit = SizeUnit.GB;
			} else {
				unit = SizeUnit.TB;
			}
		}

		switch (unit) {
		case Byte:
			return size + "B";
		case KB:
			return String.format(Locale.US, "%.2fKB", size / KB);
		case MB:
			return String.format(Locale.US, "%.2fMB", size / MB);
		case GB:
			return String.format(Locale.US, "%.2fGB", size / GB);
		case TB:
			return String.format(Locale.US, "%.2fPB", size / TB);
		default:
			return size + "B";
		}
	}

	/**
	 * 判断文件是否为assets内的文件
	 *
	 * @param filePath
	 * @return ture:是assets内的文件，false:不是assets内的文件
	 */
    public static boolean isAssetsFile(String filePath){
    	if (!TextUtils.isEmpty(filePath)){
    		if (filePath.startsWith("/assets/")){
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * 取assets目录下的文件内容
	 *
     * @param context
     * @param assetsPath
     * @return assetsPath文件的内容，内容为byte格式
     */
    public static byte[] assetsFileToByte(Context context,String assetsPath) {
		byte [] data = null;
		try {
			InputStream is = context.getClass().getResourceAsStream(assetsPath);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
	        int ch;
	        while ((ch = is.read()) != -1) {
	            bytestream.write(ch);
	        }
	        data = bytestream.toByteArray();
	        is.close();
	        bytestream.close();
		} catch (IOException e) {
			return null;
		} finally {
			
		}
		
		return data;
    }
    
    /**
     * @param context
     * @param fileName
	 * @param filePath
     * @return assets文件的文件路径
     */
	public static String getAssetsPath(Context context, String fileName, String filePath) {
		File f = new File(context.getCacheDir() + "/" + fileName);
		if (!f.exists())
			try {
				InputStream is = context.getAssets().open(filePath);
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();

				FileOutputStream fos = new FileOutputStream(f);
				fos.write(buffer);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return f.getPath();
	}

	/**
	 * 取assets目录下的文件内容
	 *
	 * @param context
	 * @param filePath
	 * @return assetsPath文件的内容，String格式
	 */
	public static String getAssetsString(Context context, String filePath){

		try {
			InputStream is = context.getAssets().open(filePath);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String result = EncodingUtils.getString(buffer, "UTF-8");
			return result;
		} catch (Exception e) {

		}
		return  null;
	}

    /**
     * 复制文件
     *
     * @param srcPath
     * @param dstPath
     * @return -1:复制失败, 0<=:复制的文件大小
     */
    public static long copy(String srcPath, String dstPath) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return -1;
        }

        File source = new File(srcPath);
        if (!source.exists()) {
            return -1;
        }

        if (srcPath.equals(dstPath)) {
            return source.length();
        }

        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            fcin = new FileInputStream(source).getChannel();
            fcout = new FileOutputStream(create(dstPath)).getChannel();
            ByteBuffer tmpBuffer = ByteBuffer.allocateDirect(4096);
            while (fcin.read(tmpBuffer) != -1) {
                tmpBuffer.flip();
                fcout.write(tmpBuffer);
                tmpBuffer.clear();
            }
            return source.length();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fcin != null) {
                    fcin.close();
                }
                if (fcout != null) {
                    fcout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    /**
     * 获取文件的大小
     *
     * @param srcPath
     * @return -1:获取失败, 0<=:文件大小
     */
    public static long getFileLength(String srcPath) {
        if (TextUtils.isEmpty(srcPath)) {
            return -1;
        }

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return -1;
        }

        return srcFile.length();
    }

    public static long save(String path, String content) {
        return save(content.getBytes(), path);
    }

    /**
     * 把数据保存到文件系统中，并且返回其大小
     *
     * @param data
     * @param filePath
     * @return 如果保存失败,则返回-1
     */
    public static long save(byte[] data, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return -1;
        }

        File f = new File(filePath);
        if(f.getParentFile() == null) {
            return -1;
        }

        if (!f.getParentFile().exists()) {// 如果不存在上级文件夹
            f.getParentFile().mkdirs();
        }
        try {
            f.createNewFile();
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(data);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return f.length();
    }

    /**
     * 移动文件
     *
     * @param srcFilePath
     * @param dstFilePath
     * @return ture:移动成功，false:移动失败
     */
    public static boolean move(String srcFilePath, String dstFilePath) {
        if (TextUtils.isEmpty(srcFilePath) || TextUtils.isEmpty(dstFilePath)) {
            return false;
        }

        File srcFile = new File(srcFilePath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File dstFile = new File(dstFilePath);
        if(dstFile.getParentFile() == null) {
            return false;
        }

        if (!dstFile.getParentFile().exists()) {// 如果不存在上级文件夹
            dstFile.getParentFile().mkdirs();
        }

        return srcFile.renameTo(dstFile);
    }

    /**
     * 新建文件
     *
     * @param filePath
     * @return 新建的File对象，如果新建失败，返回null
     */
    public static File create(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        File f = new File(filePath);
        if (!f.getParentFile().exists()) {// 如果不存在上级文件夹
            f.getParentFile().mkdirs();
        }
        try {
            f.createNewFile();
            return f;
        } catch (IOException e) {
            if(f!=null && f.exists()){
                f.delete();
            }
            return null;
        }
    }

    /**
     * 保存文件
     *
     * @param is
     * @param filePath
     * @return 保存失败，返回-1
     */
    public static long save(InputStream is, String filePath) {
        File f = new File(filePath);
        if (!f.getParentFile().exists()) {// 如果不存在上级文件夹
            f.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = ByteBufferPool.obtain(8192);
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            ByteBufferPool.recycle(bytes);
            return f.length();
        } catch (IOException e) {
            if(f!=null && f.exists()){
                f.delete();
            }
            return -1;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把文件从文件系统中读取出来
     *
     * @param path
     * @return path表示文件的byte格式的内容. 如果无法读取,则返回null
     */
    public static byte[] load(String path) {
        try {
            File f = new File(path);
            int unread = (int) f.length();
            int read = 0;
            byte[] buf = new byte[unread]; // 读取文件长度
            FileInputStream fin = new FileInputStream(f);
            do {
                int count = fin.read(buf, read, unread);
                read += count;
                unread -= count;
            } while (unread != 0);
            fin.close();
            return buf;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 把文件从文件系统中读取出来
     *
     * @param path
     * @return path表示文件的String格式的内容. 如果无法读取,则返回null.
     */
    public static String loadAsString(String path) {
        if (isFileExist(path)) {
            byte[] content = load(path);
            return new String(content);
        } else {
            return null;
        }
    }

    /**
     * 删除指定路径文件
     *
     * @param path
     * @return ture:删除成功，false:删除失败
     */
    public static boolean delete(String path) {
        if(TextUtils.isEmpty(path)){
            return false;
        }
        File f = new File(path);
        if (f.exists()) {
            f = renameOnDelete(f);
            return f.delete();
        } else {
            return false;
        }
    }

    /**
     * JVM退出时删除指定路径文件
     *
     * @param path
     * @return ture:删除成功，false:删除失败
     */
    public static void deleteOnExit(String path) {
        if(TextUtils.isEmpty(path)){
            return;
        }
        File f = new File(path);
        if (f.exists()) {
            f.deleteOnExit();
        }
    }

    /**
     * 删除指定目录
     *
     * @param path
     * @return ture:删除成功，false:删除失败
     */
    public static boolean deleteDir(String path) {
        return deleteDir(path, true);
    }

    /**
     * 删除指定目录
     *
     * @param path
     * @param rename 是否保存Temp
     * @return ture:删除成功，false:删除失败
     */
    private static boolean deleteDir(String path, boolean rename) {
        boolean success = true;
        File file = new File(path);
        if (file.exists()) {
            if (rename) {
                file = renameOnDelete(file);
            }

            File[] list = file.listFiles();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; ++i) {
                    if (list[i].isDirectory()) {
                        deleteDir(list[i].getPath(), false);
                    } else {
                        boolean ret = list[i].delete();
                        if (!ret) {
                            success = false;
                        }
                    }
                }
            }
        } else {
            success = false;
        }
        if (success) {
            file.delete();
        }
        return success;
    }

    // rename before delete to avoid lingering filesystem lock of android
    private static File renameOnDelete(File file) {
        String tmpPath = file.getParent() + "/" + System.currentTimeMillis() + "_tmp";
        File tmpFile = new File(tmpPath);
        if (file.renameTo(tmpFile)) {
            return tmpFile;
        } else {
            return file;
        }
    }

    public static boolean isFileExist(String path) {
        return !TextUtils.isEmpty(path) && new File(path).exists();
    }

    /**
     * 保存Bitmap为文件
     *
     * @param bitmap
     * @param path
     * @param recyle
     * @return ture:保存成功，false:保存失败
     */
    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recyle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recyle) {
                bitmap.recycle();
            }
        }
    }
}
