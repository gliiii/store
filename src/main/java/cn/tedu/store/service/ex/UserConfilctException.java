package cn.tedu.store.service.ex;
/**
 * 用户名冲突异常
 */
public class UserConfilctException extends ServiceException{

	private static final long serialVersionUID = 7111920410093528280L;

	public UserConfilctException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserConfilctException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UserConfilctException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserConfilctException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserConfilctException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
