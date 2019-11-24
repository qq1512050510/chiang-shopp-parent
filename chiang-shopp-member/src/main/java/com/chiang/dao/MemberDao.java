package com.chiang.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chiang.entity.UserEntity;

@Mapper
public interface MemberDao {

	@Select("select id,username,password,phone,email,created,updated,openid from mb_user where id = #{userId}")
	UserEntity findByID(@Param("userId") Long userId);

	// @Insert("INSERT INTO `mb_user`
	// (username,password,phone,email,created,updated) VALUES
	// (#{username},#{password},#{phone},#{email},#{created},#{updated});")
	@Insert("<script>" + "INSERT  INTO `mb_user`  (username,password,phone,email"
			+ "<if test='created!=null and created!=\"\"'>" + ",created" + "</if>"
			+ "<if test='updated!=null and updated!=\"\"'>" + ",updated" + "</if>"
			+ "<if test='openid!=null and openid!=\"\"'>" + ",openid" + "</if>"
			+ ") VALUES (#{username},#{password},#{phone},#{email}" + 
			"<if test='created!=null and created!=\"\"'>"+ ",#{created}" + "</if>" + 
			"<if test='updated!=null and updated!=\"\"'>" + ",#{updated}" + "</if>" + 
			"<if test='openid!=null and openid!=\"\"'>" + ",#{openid}" + "</if>" + 
			");"
			+ "</script>")
	Integer insertUser(UserEntity userEntity);

	@Select("select id,username,password,phone,email,created,updated,openid from mb_user where username = #{username} and password = #{password}")
	UserEntity login(@Param("username") String username, @Param("password") String password);

	@Select("select id,username,password,phone,email,created,updated,openid from mb_user where openid = #{openid}")
	UserEntity findByOpenIdUser(@Param("openid") String openid);

}