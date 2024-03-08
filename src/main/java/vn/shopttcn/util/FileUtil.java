package vn.shopttcn.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.ProductPicture;

public class FileUtil {
public FileUtil() {
	// TODO Auto-generated constructor stub
}
	public static String uploadFile(MultipartFile multipartFile, String folder, ServletContext servletContext) {
		String fileName = renameFile(multipartFile.getOriginalFilename());
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			try {
				String contextRoot = servletContext.getRealPath(GlobalConstant.EMPTY);
				String dirUpload = contextRoot + folder;
				File file = new File(dirUpload);
				if (!file.exists()) {
					file.mkdirs();
				}
				String filePath = dirUpload + File.separator + fileName;
				multipartFile.transferTo(new File(filePath));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	// upload multiple file, return list file name => update database
	public static List<String> uploadMultipleFile(List<MultipartFile> listMultipartFile, String folder,
			ServletContext servletContext) {
		List<String> listFileName = new ArrayList<String>();
		if (listMultipartFile.size() > 0) {
			for (MultipartFile multipartFile : listMultipartFile) {
				String fileName = renameFile(multipartFile.getOriginalFilename());
				if (!fileName.equals(GlobalConstant.EMPTY)) {
					try {
						String contextRoot = servletContext.getRealPath(GlobalConstant.EMPTY);
						String dirUpload = contextRoot + folder;
						File file = new File(dirUpload);
						if (!file.exists()) {
							file.mkdirs();
						}
						String filePath = dirUpload + File.separator + fileName;
						multipartFile.transferTo(new File(filePath));
						listFileName.add(fileName);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return listFileName;
	}

	public static String renameFile(String fileName) {
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			StringBuilder bd = new StringBuilder();
			bd.append(FilenameUtils.getBaseName(fileName)).append("-").append(System.nanoTime()).append(".")
					.append(FilenameUtils.getExtension(fileName));
			return bd.toString();
		}
		return GlobalConstant.EMPTY;
	}

	public static void delFile(String fileName, String folder, ServletContext servletContext) {
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			StringBuilder bd = new StringBuilder();
			bd.append(servletContext.getRealPath(GlobalConstant.EMPTY)).append(folder).append(File.separator)
					.append(fileName);
			File file = new File(bd.toString());
			file.delete();
		}
	}

	// listPicture: list picture của product cần xoá
	public static void delMultipleFile(List<ProductPicture> listPicture, String folder, ServletContext servletContext) {
		if (listPicture.size() > 0) {
			for (ProductPicture picture : listPicture) {
				delFile(picture.getPictureName(), folder, servletContext);
			}
		}
	}

	// listPicture: list file name của image cần xoá
	public static void delMultipleFileByName(List<String> listFileName, String folder, ServletContext servletContext) {
		if (listFileName.size() > 0) {
			for (String fileName : listFileName) {
				delFile(fileName, folder, servletContext);
			}
		}
	}

	// check file image có hợp lệ hay không
	public static boolean checkFileExtension(String fileName) {
		String fileNameExtension = FilenameUtils.getExtension(fileName);
		for (String fileExtension : GlobalConstant.FILE_EXTENSION) {
			if (fileExtension.equals(fileNameExtension)) {
				return true;
			}
		}
		return false;
	}

}
