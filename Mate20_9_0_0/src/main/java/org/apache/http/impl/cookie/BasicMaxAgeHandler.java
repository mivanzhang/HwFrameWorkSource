package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

@Deprecated
public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler {
    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        if (cookie == null) {
            throw new IllegalArgumentException("Cookie may not be null");
        } else if (value != null) {
            StringBuilder stringBuilder;
            try {
                int age = Integer.parseInt(value);
                if (age >= 0) {
                    cookie.setExpiryDate(new Date(System.currentTimeMillis() + (((long) age) * 1000)));
                    return;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Negative max-age attribute: ");
                stringBuilder.append(value);
                throw new MalformedCookieException(stringBuilder.toString());
            } catch (NumberFormatException e) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid max-age attribute: ");
                stringBuilder.append(value);
                throw new MalformedCookieException(stringBuilder.toString());
            }
        } else {
            throw new MalformedCookieException("Missing value for max-age attribute");
        }
    }
}
