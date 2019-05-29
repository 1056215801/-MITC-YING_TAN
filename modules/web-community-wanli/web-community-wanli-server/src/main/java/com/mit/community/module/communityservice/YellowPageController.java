package com.mit.community.module.communityservice;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.common.constant.Params;
import com.mit.community.entity.AdditionalParameters;
import com.mit.community.entity.TreeEntity;
import com.mit.community.entity.YellowPages;
import com.mit.community.entity.YellowPagesType;
import com.mit.community.service.YellowPagesService;
import com.mit.community.service.YellowPagesTypeService;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黄页
 *
 * @author shuyy
 * @date 2018/12/21
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/communityServiceInfo/yellow")
@Slf4j
@Api(tags = "黄页")
public class YellowPageController {

    @Autowired
    private YellowPagesTypeService yellowPagesTypeService;
    @Autowired
    private YellowPagesService yellowPagesService;

    /**
     * @param parentName  父类型
     * @param image       图片
     * @param submenuName 子菜单
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:29
     * @company mitesofor
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存黄页类型", notes = "传参：parentName 黄页父类型，" +
            " submenuName 黄页子类型， image 图标, order 排序")
    public Result save(String parentName, MultipartFile image, String submenuName, Short order) throws Exception {
        if (StringUtils.isBlank(parentName) || image == null || StringUtils.isBlank(submenuName)) {
            return Result.error("参数错误");
        }
        String imageUrl = UploadUtil.upload(image);
        yellowPagesTypeService.save(parentName,
                imageUrl, submenuName, order);
        return Result.success("保存成功");
    }

    /**
     * @param id          id
     * @param parentName  父类型
     * @param image       图片
     * @param submenuName 子菜单
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:35
     * @company mitesofor
     */
    @PatchMapping("/update")
    @ApiOperation(value = "修改黄页类型", notes = "传参：id id, parentName 黄页父类型，" +
            " submenuName 黄页子类型， image 图标")
    public Result update(Integer id, String parentName, MultipartFile image, String submenuName) throws Exception {
        if (id == null) {
            return Result.error("参数错误");
        }
        String imageUrl = null;
        if (image != null) {
            imageUrl = UploadUtil.upload(image);
        }
        yellowPagesTypeService.update(id,
                parentName, imageUrl, submenuName);
        return Result.success("修改成功");
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:37
     * @company mitesofor
     */
    @DeleteMapping("/remove")
    @ApiOperation(value = "删除黄页类型", notes = "传参：id id")
    public Result remove(Integer id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        yellowPagesTypeService.remove(id);
        return Result.success("操作成功");
    }

    /**
     * @param pageNum  当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:42
     * @company mitesofor
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询黄页类型", notes = "传参：pageNum 当前页， pageSize 分页大小")
    public Result listPage(Integer pageNum, Integer pageSize) {
        Page<YellowPagesType> page = yellowPagesTypeService.listPage(pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * @param yellowPagesTypeId 黄页类型
     * @param name              黄页名
     * @param phone             电话
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:55
     * @company mitesofor
     */
    @PostMapping("/savePage")
    @ApiOperation(value = "保存分页", notes = "传参：yellowPagesTypeId 黄页类型， name 黄页名， phone 电话")
    public Result savePage(Integer id, Integer yellowPagesTypeId, String name, String phones) {
        if (yellowPagesTypeId == null || StringUtils.isBlank(phones) || StringUtils.isBlank(name)) {
            return Result.error("参数错误");
        }
        yellowPagesService.save(yellowPagesTypeId, name, phones);
        return Result.success("保存成功");
    }

    /**
     * @param id    id
     * @param name  黄页名
     * @param phone 黄页名
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:57
     * @company mitesofor
     */
    @PatchMapping("/updatePage")
    @ApiOperation(value = "更新分页", notes = "传参：id id， name 黄页名， phone 电话")
    public Result updatePage(Integer id, String name, String phone) {
        if (id == null) {
            return Result.error("参数错误");
        }
        yellowPagesService.udpate(id, name, phone);
        return Result.success("更新成功");
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 19:57
     * @company mitesofor
     */
    @PostMapping("/removePage")
    @ApiOperation(value = "删除黄页", notes = "传参：id id")
    public Result removePage(Integer id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        yellowPagesService.remove(id);
        return Result.success("删除黄页");
    }


    /**
     * @param yellowPagesTypeId yellowPagesTypeId
     * @param pageNum           pageNum
     * @param pageSize          pageSize
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 20:04
     * @company mitesofor
     */
    @RequestMapping("/listPageList")
    @ApiOperation(value = "分页查询黄页", notes = "传参：yellowPagesTypeId 黄页类型id，pageNum 分页大小 pageSize 分页大小")
    public Result listPageList(Integer yellowPagesTypeId, String name, Integer pageNum, Integer pageSize) {
        Page<YellowPages> page = yellowPagesService.listPage(yellowPagesTypeId, name,
                pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/24 15:07
     * @Company mitesofor
     * @Description:~查询黄页分类父级菜单
     */
    @RequestMapping("/listParents")
    @ApiOperation(value = "查询分类父级菜单")
    public Result listParents() {
        Map<String, Object> map = new HashMap<>();
        List<YellowPagesType> list = yellowPagesTypeService.listByParentName(Params.ParentName);
        List<TreeEntity<YellowPagesType>> params = new ArrayList<>();
        for (YellowPagesType type : list) {
            TreeEntity<YellowPagesType> typeTreeEntity = new TreeEntity<>();
            typeTreeEntity.setT(type);
            typeTreeEntity.setName(type.getSubmenuName());
            typeTreeEntity.setType("folder");
            typeTreeEntity.setId(type.getId());
            List<YellowPagesType> children = yellowPagesTypeService.listByParentName(type.getSubmenuName());
            Map<String, Object> objectMap = new HashMap<>();
            AdditionalParameters<YellowPagesType> parameters = new AdditionalParameters<>();
            if (children.size() > 0) {
                for (YellowPagesType yellowPagesType : children) {
                    TreeEntity<YellowPagesType> treeEntity = new TreeEntity<>();
                    treeEntity.setT(yellowPagesType);
                    treeEntity.setName(yellowPagesType.getSubmenuName());
                    treeEntity.setType("item");
                    treeEntity.setId(yellowPagesType.getId());
                    objectMap.put(yellowPagesType.getSubmenuName(), treeEntity);
                }
                parameters.setChildren(objectMap);
            }
            if (parameters != null) {
                typeTreeEntity.setAdditionalParameters(parameters);
            }
            map.put(typeTreeEntity.getName(), typeTreeEntity);
            params.add(typeTreeEntity);
        }
        return Result.success(map);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/24 15:15
     * @Company mitesofor
     * @Description:~根据父级菜单名称查询子类
     */
    @RequestMapping("/listTypes")
    @ApiOperation(value = "根据父级菜单名称查询子类")
    public Result listTypes(String parentName) {
        List<YellowPagesType> list = yellowPagesTypeService.listByParentName(parentName);
        return Result.success(list);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/25 14:55
     * @Company mitesofor
     * @Description:~弹窗选择列表
     */
    @RequestMapping("listDialog")
    @ApiOperation(value = "弹窗选择列表")
    public Result listForDialog(Integer pageNum, Integer pageSize) {
        Page<YellowPagesType> page = new Page<>();
        try {
            page = yellowPagesTypeService.listDialog(pageNum, pageSize);
        } catch (Exception e) {
            return Result.error("[listForDialog]接口异常");
        }
        return Result.success(page);
    }
}
