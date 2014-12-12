package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.Date;

public final class FilePropertyHelper {

	public static String getContent(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.getPath())));
	}

	public static Date getCreationDate(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return new Date(attributes.creationTime().toMillis());
		}

		throw new Exception();
	}

	public static Date getLastAccessDate(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return new Date(attributes.lastAccessTime().toMillis());
		}

		throw new Exception();
	}

	public static Date getLastModificationDate(File file) throws Exception {
		BasicFileAttributes attributes = getBasicFileAttributeView(file);
		if (attributes != null) {
			return new Date(attributes.lastModifiedTime().toMillis());
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
        FileOwnerAttributeView attributes = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        if (attributes != null) {
        	return attributes.getOwner().getName();
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

	private static BasicFileAttributes getBasicFileAttributeView(File file)
			throws IOException {
		return Files.getFileAttributeView(Paths.get(file.getPath()),
				BasicFileAttributeView.class).readAttributes();
	}
}
