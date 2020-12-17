package com.goldwater.querycenter.service.management;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.entity.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RightService {
    @Autowired
    private RightDao rightDao;

    public Result getRightsMenuByRightId(String rightsId, int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);

        List<Menu> menuList = rightDao.getRightsMenuByRightId(rightsId);

        PageInfo p = new PageInfo<>(menuList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getRightsList(int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);

        List<Priviliges> priviligeList = rightDao.getRightByUserId("");

        PageInfo p = new PageInfo<>(priviligeList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getMenuList(int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);

        List<Menu> menuList = rightDao.getMenuList();

        PageInfo p = new PageInfo<>(menuList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getRightsMenuList(){
        User u = SessionCache.get();
        String userId = u.getUserCode();
        Result rs = new Result();

        List<Priviliges> priviligeList = rightDao.getRightByUserId(userId);

        if (priviligeList.size() > 0){
            int level = Integer.parseInt(priviligeList.get(0).getLevel());
            List<Menu> menuList = new ArrayList<>();
            List<Menu> menuNode = new ArrayList<>();

            if (level == 0 || level ==1) {
                menuNode = rightsMenus(rightDao.getMenuList(),null);
            }
            else {
                menuNode = rightsMenus(rightDao.getMenuList(), menuList);

                //清空没有子节点的父节点
                clearParent(menuNode);
            }

            //拼接菜单
            for(Menu node : menuNode) {
                menuList.add(buildMenu(node));
            }

            rs.setData(menuList);
            rs.setCode(Result.SUCCESS);
        }

        return rs;
    }

    /**
     * 	菜单权限所对应的菜单
     * @param allMenuList 所有的菜单
     * @param rightsMenuList 权限所对应的菜单(不是顶级权限,只有对应的页面菜单)
     * @return
     */
    public List<Menu> rightsMenus(List<Menu> allMenuList, List<Menu> rightsMenuList){
        List<Menu> topNode = new ArrayList<>();

        for(Menu m : allMenuList) {
            if(m.getPrtId() == null) {
                topNode.add(buildSonNode(m, allMenuList, rightsMenuList));
            }
        }

        return topNode;
    }

    /**
     * 	构建子节点
     * @param parentNode 父节点
     * @param allNode 所有的菜单
     * @param rightsMenuList 权限所对应的菜单(不是顶级权限,只有对应的页面菜单)
     * @return
     */
    public Menu buildSonNode(Menu parentNode, List<Menu> allNode
            , List<Menu> rightsMenuList){
        for(Menu node: allNode) {
            if(node.getPrtId() != null && node.getPrtId().equals(parentNode.getMenuId())) {
                if(rightsMenuList != null) {
                    //添加最后一级节点的子菜单
                    if("#".equals(node.getContent())||(!"#".equals(node.getContent()) && rightsMenuList.contains(node))) {
                        addSonNode(parentNode, node, allNode, rightsMenuList);
                    }
                }else {
                    addSonNode(parentNode, node, allNode, rightsMenuList);
                }
            }
        }
        return parentNode;
    }

    /**
     *	 把子节点添加到父节点里面
     * @param parentNode 父节点
     * @param node 当前节点
     * @param allNode 所有节点
     * @param rightsMenuList 用户有权限的节点
     * @return
     */
    public void addSonNode(Menu parentNode, Menu node, List<Menu> allNode, List<Menu> rightsMenuList) {
        if(parentNode.getChildren() == null) {
            List<Menu> children = new ArrayList<>();
            children.add(buildSonNode(node, allNode,rightsMenuList));
            parentNode.setChildren(children);
        }else{
            List<Menu>children = parentNode.getChildren();
            children.add(buildSonNode(node, allNode,rightsMenuList));
        }
    }

    /**
     * 	递归 清空没有子菜单的父菜单
     */
    public void clearParent(List<Menu> menuList) {
        for(Menu node: menuList) {
            if("#".equals(node.getContent())) {
                //如果没有子菜单，就删除
                if(node.getChildren() == null) {
                    //menuList.remove(node);
                }else {
                    clearParent(node.getChildren());
                }
            }
        }
    }

    /**
     * 	构建menu 菜单
     * @return
     */
    public Menu buildMenu(Menu parentNode){
        int menuId = 1;
        Menu parentMenu = new Menu();

        parentMenu.setId(menuId++);
        parentMenu.setMenuId(parentNode.getMenuId());
        parentMenu.setTitle(parentNode.getMenuNum());
        parentMenu.setIcon("layui-icon layui-icon-console");
        parentMenu.setOpenType("_iframe");

        if(parentNode.getChildren() != null) {
            parentMenu.setType(0);
            parentMenu.setHref("");

            List<Menu> sonNodes = parentNode.getChildren();
            List<Menu> sonMenuList = new ArrayList<>();

            for(Menu sonNode : sonNodes) {
                sonMenuList.add(buildMenu(sonNode));
            }

            parentMenu.setChildren(sonMenuList);
        }else {
            parentMenu.setType(1);
            parentMenu.setHref(parentNode.getContent().substring(1));
        }

        return parentMenu;
    }

    public Result getPrvMenuList(String privilegeId, int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);

        List<PriviligeMenu> priviligeMenuList = rightDao.getPrvMenuList(privilegeId);

        PageInfo p = new PageInfo<>(priviligeMenuList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getPrvStationList(String privilegeId, int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);

        List<PriviligeStcds> priviligeStationList = rightDao.getPrvStationList(privilegeId);

        PageInfo p = new PageInfo<>(priviligeStationList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addRights(String name, String level, String ordr, String menus, String stations){
        Result rs = new Result();
        Priviliges p = new Priviliges();
        Map map = new HashMap();

        String privilegeId = UUID.randomUUID().toString().replace("-","");

        p.setPriviligeId(privilegeId);
        p.setName(name);
        p.setLevel(level);
        p.setOrder(ordr);

        if (rightDao.insertSelective(p) > 0){
            addPrvMenuAndStation(privilegeId, menus, stations);

            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "新增权限失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result updateRights(String privilegeId, String name, String level, String ordr, String menus, String stations){
        Result rs = new Result();
        Priviliges p = new Priviliges();
        Map map = new HashMap();

        p.setPriviligeId(privilegeId);
        p.setName(name);
        p.setLevel(level);
        p.setOrder(ordr);

        if (rightDao.updateByPrimaryKeySelective(p) > 0){
            rightDao.removePrvMenu(privilegeId);
            rightDao.removePrvStation(privilegeId);

            addPrvMenuAndStation(privilegeId, menus, stations);

            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "修改权限失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    private void addPrvMenuAndStation(String privilegeId, String menus, String stations){
        if ((!StringUtil.isBlank(menus)) && (!StringUtil.isBlank(privilegeId)))
            rightDao.addPrivilegeMenu(privilegeId, Arrays.asList(menus.split(",")));

        if(!StringUtil.isBlank(stations) && stations.length() > 0)
            rightDao.addPrvStation(privilegeId, Arrays.asList(stations.split(",")));
    }

    public Result removeRights(String privilegeId){
        Result rs = new Result();
        Map map = new HashMap();

        if (rightDao.removePrvMenu(privilegeId) > 0) {
            rightDao.removePrvStation(privilegeId);
            rightDao.removeUserRight(privilegeId);

            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "删除权限失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result addMenu(String menuNm, String content, String ordr, String prt_id){
        Result rs = new Result();
        String menuId=UUID.randomUUID().toString().replace("-","");
        Map map = new HashMap();

        if (rightDao.addMenu(menuId, menuNm, content, ordr, prt_id) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "新增菜单失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result updateMenu(String menuId, String menuNm, String content, String ordr){
        Result rs = new Result();
        Map map = new HashMap();

        if (rightDao.updateMenu(menuId, menuNm, content, ordr) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "修改菜单失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result removeMenu(String menuId){
        Result rs = new Result();
        Map map = new HashMap();

        if (rightDao.removeMenu(Arrays.asList(menuId.split(","))) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "删除菜单失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }
}
