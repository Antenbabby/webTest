package top.antennababy.demo.web.webtest.demos.common;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * 证件类型
 */
public enum CertificateTypeEnum {
    // 1是身份证,2是港澳通行证3是护照
    // 出生证明、军官证、台胞证、港澳回乡证、外国人居留证、外国人永久居留身份证
    ID_CARD      (1, "居民身份证"),
    HK_MACAO_PASS(2, "港澳通行证"),
    PASSPORT     (3, "护照"),
    BIRTH_CERTIFICATE(4, "出生证明"),
    OFFICER_CERTIFICATE(5, "军官证"),
    TAIWAN_PASS(6, "台胞证"),
    HK_MACAO_RETURN_HOME(7, "港澳回乡证"),
    FOREIGN_RESIDENCE(8, "外国人居留证"),
    FOREIGN_PERMANENT_RESIDENCE(9, "外国人永久居留身份证");


    private final Integer code;
    private final String name;

    CertificateTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public String  getCodeStr(){
        return StrUtil.toStringOrNull(code);
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getCertName(Integer code) {
        CertificateTypeEnum[] values = CertificateTypeEnum.values();
        CertificateTypeEnum certificateTypeEnum = Arrays.stream(values).filter(x -> x.getCode().equals(code)).findFirst().orElse(null);
        if (certificateTypeEnum == null) {
            return null;
        }else {
            return certificateTypeEnum.getName();
        }
    }

}
