package top.antennababy.demo.web.webtest.demos.web

import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.antennababy.demo.web.webtest.demos.service.ConfigService

import javax.annotation.Resource

@RestController
@RequestMapping("/config")
@Slf4j
class ConfigGroovyController {

    @Resource
    ConfigService configService;

    @GetMapping("/{id}")
    def get(@PathVariable Integer id) {
        return configService.getById(id);
    }
}
