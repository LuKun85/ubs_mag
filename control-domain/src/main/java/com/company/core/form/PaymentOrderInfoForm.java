package com.company.core.form;

public class PaymentOrderInfoForm  extends BaseForm {
	
	private String paymentOrderCode;//订单编号; 
	private String userName; //用户名;          
	private String userPhone; //用户手机号;     
	private String paymentChannel;//交易类型;   
	private String itemCode; //交易项目;        
	private String proOrg; //供应商;            
	private String itemName; //机构名称;        
	private String orderAmount; //订单总额;     
	private String orderStatus;//订单状态;      
	private String sendOrderStatus;//发货状态;  
	private String createDate;//创建时间;       
	private String updateDate;//更新时间;       
	private String transId; //交易流水号;       
	private String channelId; //渠道号      
	private String retMsg;//返回消息
	private String typeCode;
    private String typeName;
	//附加字段
	private String startTime;
	private String endTime;
	private String orderStatusDesc;
    private String sendOrderStatusDesc;
    
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public String getSendOrderStatusDesc() {
		return sendOrderStatusDesc;
	}
	public void setSendOrderStatusDesc(String sendOrderStatusDesc) {
		this.sendOrderStatusDesc = sendOrderStatusDesc;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getPaymentOrderCode() {
		return paymentOrderCode;
	}
	public void setPaymentOrderCode(String paymentOrderCode) {
		this.paymentOrderCode = paymentOrderCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getProOrg() {
		return proOrg;
	}
	public void setProOrg(String proOrg) {
		this.proOrg = proOrg;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getSendOrderStatus() {
		return sendOrderStatus;
	}
	public void setSendOrderStatus(String sendOrderStatus) {
		this.sendOrderStatus = sendOrderStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
