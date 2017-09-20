package kr.ymtech.ojt.utils;

import java.util.UUID;


/**
 * Util
 * 
 * @author jhlee
 *
 */
public class CommonUtils {
	
	public static final String DEFAULT_VNF_CATEGORY = "Basic Download";
	
	/**
	 * UUID를 생성 한다.
	 * 
	 * 2017. 08. 16 jhlee
	 * 
	 * @return
	 */
	public static  String createUuid(){

		UUID uuid = UUID.randomUUID();
		
		return	uuid.toString(); 
	}
	
	
}
