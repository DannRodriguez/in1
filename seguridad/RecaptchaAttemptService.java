package mx.ine.procprimerinsa.seguridad;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service("recaptchaAttemptService")
public class RecaptchaAttemptService {
	
	private static final int MAX_ATTEMPT = 4;
	private static final int EXPIRE_TIME_MINUTS = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public RecaptchaAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
					        		.expireAfterWrite(EXPIRE_TIME_MINUTS, TimeUnit.MINUTES)
					        		.build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void reCaptchaSucceeded(final String key) {
        attemptsCache.invalidate(key);
    }

    public void reCaptchaFailed(final String key) {
        int attempts = attemptsCache.getUnchecked(key);
        attempts++;
        if(attempts > MAX_ATTEMPT) return;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String key) {
        return attemptsCache.getUnchecked(key) >= MAX_ATTEMPT;
    }

	public static int getExpireTimeMinuts() {
		return EXPIRE_TIME_MINUTS;
	}

}
