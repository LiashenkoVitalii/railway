package com.liashenko.app.service.dto;

//Contains messages and result of performed operation to transfer to front-end with JSON
public class ResponseMsgDto {
    private static final long serialVersionUID = 1L;

    private String message;
    private String message2;
    private boolean success;

    public ResponseMsgDto() {
    }

    public ResponseMsgDto(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResponseMsgDto(String message, String message2, boolean success) {
        this.message = message;
        this.message2 = message2;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseMsgDto)) return false;

        ResponseMsgDto that = (ResponseMsgDto) o;

        if (success != that.success) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return message2 != null ? message2.equals(that.message2) : that.message2 == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (message2 != null ? message2.hashCode() : 0);
        result = 31 * result + (success ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseMsgDto{");
        sb.append("message='").append(message).append('\'');
        sb.append(", message2='").append(message2).append('\'');
        sb.append(", success=").append(success);
        sb.append('}');
        return sb.toString();
    }
}
