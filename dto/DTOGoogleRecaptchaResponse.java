package mx.ine.procprimerinsa.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "success", "challenge_ts", "hostname", "error-codes" })
public class DTOGoogleRecaptchaResponse {
	
	@JsonProperty("success")
    private boolean success;
	
    @JsonProperty("challenge_ts")
    private String challengeTimeStamp;
    
    @JsonProperty("hostname")
    private String hostname;
    
    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;
    
    enum ErrorCode {
        MissingSecret, InvalidSecret, MissingResponse, InvalidResponse, BadRequest, TimeoutOrDuplicate;

        private static Map<String, ErrorCode> errorsMap = new HashMap<>(6);

        static {
            errorsMap.put("missing-input-secret", MissingSecret);
            errorsMap.put("invalid-input-secret", InvalidSecret);
            errorsMap.put("missing-input-response", MissingResponse);
            errorsMap.put("invalid-input-response", InvalidResponse);
            errorsMap.put("bad-request", BadRequest);
            errorsMap.put("timeout-or-duplicate", TimeoutOrDuplicate);
        }

        @JsonCreator
        public static ErrorCode forValue(final String value) {
            return errorsMap.get(value.toLowerCase());
        }
    }

    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChallengeTimeStamp() {
        return challengeTimeStamp;
    }

    public void setChallengeTimeStamp(String challengeTimeStamp) {
        this.challengeTimeStamp = challengeTimeStamp;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setErrorCodes(ErrorCode[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public ErrorCode[] getErrorCodes() {
        return errorCodes;
    }

    @JsonIgnore
    public boolean hasClientError() {
        final ErrorCode[] errors = getErrorCodes();
        if (errors == null) {
            return false;
        }
        for (final ErrorCode error : errors) {
            switch (error) {
            case InvalidResponse, MissingResponse, BadRequest:
                return true;
            default:
                break;
            }
        }
        return false;
    }

	@Override
	public String toString() {
		return "DTOGoogleRecaptchaResponse [success=" + success + ", challengeTimeStamp=" + challengeTimeStamp
				+ ", hostname=" + hostname + ", errorCodes=" + Arrays.toString(errorCodes) + "]";
	}

}
