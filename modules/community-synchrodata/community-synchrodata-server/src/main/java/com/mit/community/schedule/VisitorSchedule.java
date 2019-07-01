package com.mit.community.schedule;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.Visitor;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.VisitorMessageService;
import com.mit.community.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 访客定时同步
 *
 * @author shuyy
 * @date 2018/11/21
 * @company mitesofor
 */
@Component
public class VisitorSchedule {

    private final VisitorService visitorService;

    private final ClusterCommunityService clusterCommunityService;

    private final VisitorMessageService visitorMessageService;

    @Autowired
    public VisitorSchedule(VisitorService visitorService, ClusterCommunityService clusterCommunityService, VisitorMessageService visitorMessageService) {
        this.visitorService = visitorService;
        this.clusterCommunityService = clusterCommunityService;
        this.visitorMessageService = visitorMessageService;
    }

    //@Scheduled(cron = "0 */30 * * * ?")
    @Scheduled(cron = "0 */20 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport() {
        List<Visitor> dataList = visitorService.list();
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        List<Visitor> visitors = visitorService.listFromDnakeByCommunityCodeList(communityCodeList);
        Map<Integer, Visitor> map = Maps.newHashMapWithExpectedSize(dataList.size());
        dataList.forEach(item -> map.put(item.getVisitorId(), item));
        List<Visitor> addList = Lists.newArrayListWithCapacity(visitors.size());
        List<Visitor> deleteList = Lists.newArrayListWithCapacity(visitors.size());
        List<Visitor> updateList = Lists.newArrayListWithCapacity(visitors.size());
        for (int i = 0; i < visitors.size(); i++) {
            Visitor dnakeVisitor = visitors.get(i);
            Integer visitorId = dnakeVisitor.getVisitorId();
            Visitor dataVisitor = map.get(visitorId);
            if (dataVisitor == null) {
                addList.add(dnakeVisitor);
            } else {
                if (dnakeVisitor.getVisitorStatus().equals(dataVisitor.getVisitorStatus())) {
                    map.remove(visitorId);
                } else {
                    dnakeVisitor.setId(dataVisitor.getId());
                    updateList.add(dnakeVisitor);
                    map.remove(visitorId);
                }
            }
        }
        map.forEach((key, value) -> {
            deleteList.add(value);
        });
        if (!addList.isEmpty()) {
            visitorService.insertBatch(addList);
        }
        if (!deleteList.isEmpty()) {
            visitorService.deleteBatchIds(deleteList.parallelStream().map(Visitor::getVisitorId).collect(Collectors.toList()));
        }
        if (!updateList.isEmpty()) {
            visitorService.updateBatchById(updateList);
        }
        updateList.addAll(addList);
        if (!updateList.isEmpty()) {
            updateList.forEach(item -> {
                Integer visitorStatus = item.getVisitorStatus();
                String title = "";
                if (visitorStatus.equals(1)) {
                    //已到访
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":已到访";
                }
                if (visitorStatus.equals(2)) {
                    //未到访
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":未到访";
                }
                if (visitorStatus.equals(3)) {
                    //已离开
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":已离开";
                }
                if (visitorStatus.equals(4)) {
                    //手动签离
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":手动签离";
                }
                if (visitorStatus.equals(5)) {
                    //已撤销
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":已撤销";
                }
                if (visitorStatus.equals(6)) {
                    //到访超时
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":到访超时";
                }
                if (visitorStatus.equals(7)) {
                    //超时手动签离
                    title = item.getCommunityName() + item.getZoneName()
                            + item.getBuildingName() + item.getUnitName() +
                            item.getRoomNum() + item.getVisitorName() + ":超时手动签离";
                }
                visitorMessageService.save(item.getInviteMobile(), title
                );
            });
        }

    }
}

