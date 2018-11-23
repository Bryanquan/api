package com.cn.api.entity;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESOperator {
	private String sKey ="0123456789abcdef";
	private String ivParameter="0123456789abcdef";
	private static AESOperator instance=null;
	private final Base64.Decoder decoder =Base64.getDecoder();
	private final Base64.Encoder encoder =Base64.getEncoder();
	private AESOperator(){
		
	}
	public static AESOperator getInstance(){
		if( instance == null){
			instance= new AESOperator();
			
		}
		return instance;
	}
	//加密
	public String encrypt( String sSrc )throws Exception{
		Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding");
		byte[] raw =sKey.getBytes();
		SecretKeySpec skeySpec =new SecretKeySpec( raw, "AES");
		IvParameterSpec iv = new IvParameterSpec( ivParameter.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv );
		byte[] encrypted = cipher.doFinal( sSrc.getBytes( "utf-8"));
		return  encoder.encodeToString( encrypted);
	}
	public String decrypt( String sSrc) throws Exception{
		try{
			byte[]raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec( raw, "AES");
			Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5padding");
			IvParameterSpec iv = new IvParameterSpec( ivParameter.getBytes());
			cipher.init( Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[]encrypted1 = decoder.decode( sSrc);
			byte[]original = cipher.doFinal( encrypted1);
			String originalString = new String( original, "utf-8");
			return originalString;
		}
		catch( Exception ex){
			return null;
		}
	}
	public static void main( String[] args ) throws Exception{
		String cSrc = "wwww.wenhq.com";
		System.out.println( cSrc );
		long lStart = System.currentTimeMillis();
		String enString = AESOperator.getInstance().encrypt(cSrc);
		System.out.println("enc:"+ enString );
		long lUseTime = System.currentTimeMillis()-lStart;
		System.out.println("min:"+lUseTime );
		String DeString = AESOperator.getInstance().decrypt(enString);
		System.out.println( "dec:"+ DeString);
		
		
	}
	
}