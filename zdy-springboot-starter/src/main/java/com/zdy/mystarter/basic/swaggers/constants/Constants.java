package com.zdy.mystarter.basic.swaggers.constants;

/**
 *
 */
public class Constants {
	public static String SessionContext="sessionContext";
	// biz 是否有效
	public static String BIZ_Valid = "bizValid";
	// biz flowid
	public static String FLOW_ID = "flowId";
	// biz opid
	public static String OP_ID = "opId";
	public static String FILTER_PRE = "pre";
	public static String FILTER_POST = "post";
	public static String BizServiceDefine = "BizServiceDefine";
	public static String PARAM_MAP = "parameterMap";

	// 请求头参数列表
	public static String HeaderFiled[] = { "serviceId", "channelId", "appId", "userId", "custId", "branchId", "currentBusinessCode", "requestSequenceNo", "originalSequenceNo",
			"requestTime", "pageSize", "offset", "hostIp", "EMP-ID","clientIP", "Authorization"};

	// 请求头
	public static String SERVICE_ID = "serviceId";//请求服务id
	public static String CHANNEL_ID = "channelId";//调用者渠道号
	public static String APP_ID = "appId";//调用者应用id
	public static String TARGET_APP_ID = "targetAppId";//调用者应用id
	public static String USER_ID = "userId";//用户id
	public static String CUST_ID = "custId";//客户id
	public static String BRANCH_ID = "branchId";//机构id
	public static String BUSINESS_CODE = "currentBusinessCode";//交易码
	public static String CurrentFlow = "currentFlow";//当前交易biz
	public static String REQUEST_SEQUENCE_NO = "requestSequenceNo";//请求序列号
	public static String REQUEST_TIME = "requestTime";
	public static String PAGE_SIZE = "pageSize";
	public static String PAGE_NO = "pageNo";
	public static String HOST_IP = "hostIp";
	public static String AUTHORIZATION = "Authorization";

	// 响应信息
	public static String RESPONSE_CODE = "ec";
	public static String RESPONSE_MESSAGE = "em";
	public static String RETURN_TIME = "returnTime";
	public static String RECORD_COUNT = "recordCount";
	public static String RET_SUCCESS = "0";
	public static String RET_COMMON_ERROR = "1001";
	public static int Max_Parameter_Size = 200;
	

}
