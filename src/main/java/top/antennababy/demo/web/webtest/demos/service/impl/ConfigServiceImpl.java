package top.antennababy.demo.web.webtest.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.antennababy.demo.web.webtest.demos.domain.Config;
import top.antennababy.demo.web.webtest.demos.service.ConfigService;
import top.antennababy.demo.web.webtest.demos.mapper.ConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author laikang
* @description 针对表【CONFIG】的数据库操作Service实现
* @createDate 2024-06-05 13:53:57
*/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config>
    implements ConfigService{

}




