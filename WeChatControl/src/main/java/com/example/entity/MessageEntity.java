package com.example.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.UUID;

/**
 * 微信消息实体类，集合了文本，语音，图片，视频，地理位置，链接的实体类
 * @author 刘欢
 * @Date 2020/12/1
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageEntity {

	private String id;

	private Date create_time;

	/**
	 * 该公众号绑定的开发者的微信号
	 */
	private String ToUserName;

	/**
	 * 发送该条消息的用户的openid
	 */
	private String FromUserName;

	/**
	 * 该条消息的创建时间(时间戳)整型
	 */
	private Long CreateTime;

	/**
	 * 该条消息的类型
	 * text:文本
	 * image:图片
	 * voice:语音
	 * video:视频
	 * shortvideo:小视频
	 * location:地理位置
	 * link:链接
	 * event:菜单栏触发
	 */
	private String MsgType;

	/**
	 * 消息内容
	 */
	private String Content;

	/**
	 * 消息id,该条消息的唯一标识，64位整型
	 */
	private String MsgId;

	/**
	 * 图片消息的图片链接（由系统生成）
	 */
	private String PicUrl;

	/**
	 * 媒体id，可以调用获取临时素材接口拉取数据
	 */
	private String MediaId;

	/**
	 * 语音格式，如amr，speex等
	 */
	private String Format;

	/**
	 * 语音识别结果，UTF8编码
	 */
	private String Recognition;

	/**
	 *视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	private String ThumbMediaId;

	/**
	 * 位置消息的，地理位置纬度
	 */
	private String Location_X;

	/**
	 * 位置消息的，地理位置经度
	 */
	private String Location_Y;

	/**
	 * 地图缩放大小
	 */
	private String Scale;

	/**
	 * 地理位置信息
	 */
	private String Label;

	/**
	 *链接消息标题
	 */
	private String Title;

	/**
	 * 链接消息描述
	 */
	private String Description;

	/**
	 * 链接消息的消息链接
	 */
	private String Url;

	/**
	 * 当消息类型为event菜单事件类型的时候，该指端存储事件的种类
	 */
	private String Event;

	/**
	 * 菜单中事件设置的key值
	 */
	private String EventKey;

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}


	/**
	 * 自动生成id和创建时间
	 */
	MessageEntity(){
		//id是64为字符串
		this.id = UUID.randomUUID().toString().replaceAll("-","");
		this.create_time = new Date();
	}

	@Override
	public String toString() {
		return "MessageEntity{" +
				"id='" + id + '\'' +
				", create_time=" + create_time +
				", ToUserName='" + ToUserName + '\'' +
				", FromUserName='" + FromUserName + '\'' +
				", CreateTime=" + CreateTime +
				", MsgType='" + MsgType + '\'' +
				", Content='" + Content + '\'' +
				", MsgId='" + MsgId + '\'' +
				", PicUrl='" + PicUrl + '\'' +
				", MediaId='" + MediaId + '\'' +
				", Format='" + Format + '\'' +
				", Recognition='" + Recognition + '\'' +
				", ThumbMediaId='" + ThumbMediaId + '\'' +
				", Location_X='" + Location_X + '\'' +
				", Location_Y='" + Location_Y + '\'' +
				", Scale='" + Scale + '\'' +
				", Label='" + Label + '\'' +
				", Title='" + Title + '\'' +
				", Description='" + Description + '\'' +
				", Url='" + Url + '\'' +
				'}';
	}
}
