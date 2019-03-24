package com.example.muscishow.until;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.engines.RC6Engine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.bouncycastle.util.encoders.UrlBase64Encoder;

/**
 * crypto utility functions
 *
 * @author eric
 */
public class CryptoUtil {
	private static byte[] DEFAULT_KEY = "JaDeFoRtUnE2006!@#$%^&*()_+"
			.getBytes();

	public static byte[] BlockCipherProcess(BlockCipher engine,
			boolean encrypt, byte[] data, byte[] key) {
		KeyParameter kp = new KeyParameter(key);
		PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

		cipher.init(encrypt, kp);

		byte[] out = null;
		try {
			out = new byte[cipher.getOutputSize(data.length)];
			int len1 = cipher.processBytes(data, 0, data.length, out, 0);
			cipher.doFinal(out, len1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return out;
	}

	public static byte[] rc6(boolean encrypt, byte[] data, byte[] key) {
		return BlockCipherProcess(new RC6Engine(), encrypt, data, key);
	}

	public static byte[] blowfish(boolean encrypt, byte[] data, byte[] key) {
		return BlockCipherProcess(new BlowfishEngine(), encrypt, data, key);
	}

	public static String base64_encode(byte[] data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Base64Encoder ube = new Base64Encoder();
		try {
			ube.encode(data, 0, data.length, baos);
		} catch (IOException e) {
		}

		return baos.toString();
	}

	public static byte[] base64_decode(String s) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Base64Encoder ube = new Base64Encoder();
		try {
			ube.decode(s, baos);
		} catch (IOException e) {
		}

		return baos.toByteArray();
	}

	public static String base64url_encode(byte[] data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UrlBase64Encoder ube = new UrlBase64Encoder();
		try {
			ube.encode(data, 0, data.length, baos);
		} catch (IOException e) {
		}

		return baos.toString();
	}

	public static byte[] base64url_decode(String s) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UrlBase64Encoder ube = new UrlBase64Encoder();
		try {
			ube.decode(s, baos);
		} catch (IOException e) {
		}

		return baos.toByteArray();
	}

	public static String hex_encode(byte[] data) {
		return Hex.encodeHexString(data);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		HexEncoder he = new HexEncoder();
//		try {
//			he.encode(data, 0, data.length, baos);
//		} catch (IOException e) {
//		}
//
//		return baos.toString();
	}

	public static byte[] hex_decode(String s) {
		try
		{
			return Hex.decodeHex(s.toCharArray());
		} catch (DecoderException e)
		{
			throw new IllegalStateException("Hex Decoder exception", e);
		}
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		HexEncoder he = new HexEncoder();
//		try {
//			he.decode(s, baos);
//		} catch (IOException e) {
//		}
//
//		return baos.toByteArray();
	}

	/**
	 * fast encrypt data for passing by url
	 *
	 * @param data
	 * @return
	 */
	public static String fast_url_encrypt(byte[] data) {
		return base64url_encode(rc6(true, data, DEFAULT_KEY));
	}

	/**
	 * decrypt data resulted by fast_url_encrypt.
	 *
	 * <b>NOTE:return data may have zero padding</b>
	 *
	 * @param s
	 * @return
	 */
	public static byte[] fast_url_decrypt(String s) {
		return rc6(false, base64url_decode(s), DEFAULT_KEY);
	}

	/**
	 * �ַ�����Ĭ�ϸ�ʽ����
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		MD5Digest digest = new MD5Digest();
		byte[] bytes;
		try {
			bytes = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			bytes = str.getBytes();
		}
		byte[] buf = new byte[digest.getDigestSize()];

		digest.update(bytes, 0, bytes.length);
		digest.doFinal(buf, 0);
		return Hex.encodeHexString(buf);
	}

}
