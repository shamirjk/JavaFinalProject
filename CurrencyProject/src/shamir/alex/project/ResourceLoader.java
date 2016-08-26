package shamir.alex.project;

import java.io.InputStream;

public class ResourceLoader 
{
	public static InputStream load(String path) {
		InputStream inputStream = ResourceLoader.class.getResourceAsStream(path);
		if(inputStream == null) {
			inputStream = ResourceLoader.class.getResourceAsStream("/"+ path);
		}
		return inputStream;
	}
}
