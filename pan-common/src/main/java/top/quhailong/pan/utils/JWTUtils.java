
package top.quhailong.pan.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * ClassName: JWTUtils <br/>
 * Function:Json Web Token的java实现<br/>
 * date: 2017年9月4日 下午5:26:01 <br/>
 *
 * @author guooo
 * @version
 * @since JDK 1.6
 */
public class JWTUtils {
	
	static String SECRETKEY = "nimadetou";

	/**
	 * 由字符串生成加密key
	 * 
	 * @return
	 */
	public static Key generalKey(SignatureAlgorithm signatureAlgorithm) {
		byte[] apiKeySecretBytes = SECRETKEY.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		//byte[] encodedKey = Base64.decodeBase64(stringKey);
		//SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return signingKey;
	}

	/**
	 * createJWT: 创建jwt<br/>
	 *
	 * @author guooo
	 * @param id
	 *            唯一id，uuid即可
	 * @param subject
	 *            json形式字符串或字符串，增加用户非敏感信息存储，如user tid，与token解析后进行对比，防止乱用
	 * @param ttlMillis
	 *            有效期
	 * @param stringKey
	 * @return jwt token
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Key key = generalKey(signatureAlgorithm);
		JwtBuilder builder = Jwts.builder().setId(id).setSubject(subject)
				.signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * parseJWT: 解密jwt <br/>
	 *
	 * @author guooo
	 * @param jwt
	 * @param stringKey
	 * @return
	 * @throws ExpiredJwtException
	 * @throws UnsupportedJwtException
	 * @throws MalformedJwtException
	 * @throws SignatureException
	 * @throws IllegalArgumentException
	 * @since JDK 1.6
	 */
	public static Claims parseJWT(String jwt,byte[] signKey) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException {
		Claims claims = Jwts.parser().setSigningKey(signKey).parseClaimsJws(jwt).getBody();
		return claims;
	}
}
