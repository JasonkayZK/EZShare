package top.jasonkayzk.ezshare.gen.service.impl;

import top.jasonkayzk.ezshare.gen.entity.TUser;
import top.jasonkayzk.ezshare.gen.mapper.TUserMapper;
import top.jasonkayzk.ezshare.gen.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Jasonkay
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
