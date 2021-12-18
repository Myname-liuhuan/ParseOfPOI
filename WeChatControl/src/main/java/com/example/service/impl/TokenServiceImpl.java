package com.example.service.impl;

import com.example.entity.MessageEntity;
import com.example.dao.MessageMapper;
import com.example.service.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 刘欢
 * @Date 2020/11/30
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	MessageMapper messageMapper;

	@Override
	public String parseToken(HttpServletRequest request) {
		System.out.println("进入端口");
		//token是和微信配置页面约定统一的
		final String token = "wxtest";
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		//参数为空直接返回
		if(signature == null || timestamp == null || nonce == null || echostr == null){
			return null;
		}

		System.out.println("signature:" + signature + "\ntimestamp:" + timestamp + "\nnonce:" + nonce + "\nechostr:" + echostr);

		//先将token,timestamp,nonce按照字典排序(可以使用String数组或者list,最后只要用对应的数组，集合工具类(Arrays.sort; Collections.sort())排序就好了)
		String[] strings = {token,timestamp,nonce};
		Arrays.sort(strings);

		//再将排序好的数组加密,apache.
		String mySignature = DigestUtils.sha1Hex(strings[0] + strings[1] + strings[2]);
		System.out.println(mySignature);
		if(signature.equals(mySignature)){
			System.out.println(true);
			return echostr;
		}
		//其他情况不做处理
		return null;
	}

	/**
	 * 用HttpServletRequest 来接收请求，然后使用dom4j将数据解析出来
	 * @param request
	 * @return
	 */
	@Override
	public String parseMessage(HttpServletRequest request) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(request.getInputStream());
			Element rootElement = document.getRootElement();
			System.out.println("根节点:" + rootElement.getName() + ":" + rootElement.getData());
			List<Element> list = rootElement.elements();
			for (Element element : list) {
				System.out.println(element.getName() + ":" + element.getData());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public String parseMessage(MessageEntity messageEntity) {
		try {
			//首先将每条进来的数据保存到数据库
			int i = messageMapper.insertOfEntity(messageEntity);
			if (i == 1){
				System.out.println("插入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}


	public void printRequestParams(HttpServletRequest request){
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry: parameterMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
		}
	}

}
