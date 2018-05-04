package cn.dy.sys.exception;

public class CustomRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -885276584761214095L;

    private String code;

    public CustomRuntimeException(final String message) {
        super(message);
    }

    public CustomRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
