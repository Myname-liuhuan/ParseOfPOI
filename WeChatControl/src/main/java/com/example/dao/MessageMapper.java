package com.example.dao;

import com.example.entity.MessageEntity;
import org.springframework.stereotype.Repository;

/**
 * @author 刘欢
 * @Date 2020/12/1
 */
@Repository
public interface MessageMapper {

	/**
	 * 将每次发送给公众号的消息存到数据库
	 * @param messageEntity
	 * @return
	 */
	Integer insertOfEntity(MessageEntity messageEntity);

}
