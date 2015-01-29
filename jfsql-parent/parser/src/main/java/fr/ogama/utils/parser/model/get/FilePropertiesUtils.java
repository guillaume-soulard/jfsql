package fr.ogama.utils.parser.model.get;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilePropertiesUtils {

	public static String datePattern = "yyyy/MM/dd HH:mm:ss";

	public static String getContent(File file) throws IOException {
		if (file != null && file.exists() && file.isFile() && file.canRead()) {
			return new String(Files.readAllBytes(Paths.get(file.getPath())));
		}
		
		return "";
	}

	public static Date getCreationDate(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return parseDateFromCurrentLocal(dateToString(new Date(attributes
					.creationTime().toMillis())));
		}

		throw new Exception();
	}

	public static Date getLastAccessDate(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return parseDateFromCurrentLocal(dateToString(new Date(attributes
					.lastAccessTime().toMillis())));
		}

		throw new Exception();
	}

	public static Date getLastModificationDate(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return parseDateFromCurrentLocal(dateToString(new Date(attributes
					.lastModifiedTime().toMillis())));
		}

		throw new Exception();
	}

	public static Object getKey(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return attributes.fileKey();
		}

		throw new Exception();
	}

	public static String getOwner(File file) throws Exception {
		Path path = Paths.get(file.getPath());
		FileOwnerAttributeView attributes = Files.getFileAttributeView(path,
				FileOwnerAttributeView.class);
		if (attributes != null) {
			return attributes.getOwner().getName().replaceAll("\\\\", "/");
		}
		throw new Exception();
	}

	public static Long getSize(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return attributes.size();
		}

		throw new Exception();
	}

	public static Date parseDateFromCurrentLocal(String date)
			throws ParseException {
		return new SimpleDateFormat(datePattern).parse(date);
	}

	public static String dateToString(Date date) {
		return new SimpleDateFormat(datePattern).format(date);
	}

	private static BasicFileAttributes getBasicFileAttributeView(File file)
			throws IOException {
		return Files.getFileAttributeView(Paths.get(file.getPath()),
				BasicFileAttributeView.class).readAttributes();
	}
}
