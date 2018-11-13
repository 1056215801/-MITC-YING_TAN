package com.mit.admin.rest;

import com.mit.admin.biz.MenuBiz;
import com.mit.admin.biz.UserBiz;
import com.mit.admin.entity.Menu;
import com.mit.admin.vo.AuthorityMenuTree;
import com.mit.admin.vo.MenuTree;
import com.mit.admin.constant.AdminCommonConstant;
import com.mit.common.rest.BaseController;
import com.mit.common.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:21
 * @company mitesofor
*/
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController<MenuBiz, Menu> {
    @Autowired
    private UserBiz userBiz;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> list(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return baseBiz.selectByExample(example);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTree> getTree(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return getMenuTree(baseBiz.selectByExample(example), AdminCommonConstant.ROOT);
    }



    @RequestMapping(value = "/system", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> getSystem() {
        Menu menu = new Menu();
        menu.setParentId(AdminCommonConstant.ROOT);
        return baseBiz.selectList(menu);
    }

    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTree> listMenu(Integer parentId) {
        try {
            if (parentId == null) {
                parentId = this.getSystem().get(0).getId();
            }
        } catch (Exception e) {
            return new ArrayList<MenuTree>();
        }
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        Example example = new Example(Menu.class);
        Menu parent = baseBiz.selectById(parentId);
        example.createCriteria().andLike("path", parent.getPath() + "%").andNotEqualTo("id",parent.getId());
        return getMenuTree(baseBiz.selectByExample(example), parent.getId());
    }

    @RequestMapping(value = "/authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorityMenuTree> listAuthorityMenu() {
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (Menu menu : baseBiz.selectListAll()) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, AdminCommonConstant.ROOT);
    }

    @RequestMapping(value = "/user/authorityTree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTree> listUserAuthorityMenu(Integer parentId){
        int userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
        try {
            if (parentId == null) {
                parentId = this.getSystem().get(0).getId();
            }
        } catch (Exception e) {
            return new ArrayList<MenuTree>();
        }
        return getMenuTree(baseBiz.getUserAuthorityMenuByUserId(userId),parentId);
    }

    @RequestMapping(value = "/user/system", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> listUserAuthoritySystem() {
        int userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
        return baseBiz.getUserAuthoritySystemByUserId(userId);
    }
    /**
     * 构造树，构造的树的结构并非是树状结构，只是记录每个节点的子节点。由前台构造树状结构
     * @param menus
     * @param root
     * @return
     */
    private List<MenuTree> getMenuTree(List<Menu> menus,int root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (Menu menu : menus) {
            node = new MenuTree();
            BeanUtils.copyProperties(menu, node);
            node.setLabel(menu.getTitle());
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root) ;
    }


}
