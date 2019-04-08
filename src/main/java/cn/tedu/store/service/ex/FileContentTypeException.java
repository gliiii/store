package cn.tedu.store.service.ex;
/**
 * 文件类型异常
 */
public class FileContentTypeException extends FileUploadException{
	private static final long serialVersionUID = 8423050873902760161L;

	public FileContentTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
