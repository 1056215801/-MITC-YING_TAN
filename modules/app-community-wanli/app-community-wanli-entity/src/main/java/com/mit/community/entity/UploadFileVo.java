package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/7 9:19
 * @Company mitesofor
 * @Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadFileVo {

    private String fileDomain;

    private String fileName;

    private String originalFileName;

    private String filePath;
}
