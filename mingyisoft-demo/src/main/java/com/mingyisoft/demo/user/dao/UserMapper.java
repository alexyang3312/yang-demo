package com.mingyisoft.demo.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingyisoft.demo.user.model.User;

//@Mapper
//@Component
public interface UserMapper extends BaseMapper<User>{
//    Integer insert(UserDomain record);
//    List<UserDomain> selectUsers();
}
