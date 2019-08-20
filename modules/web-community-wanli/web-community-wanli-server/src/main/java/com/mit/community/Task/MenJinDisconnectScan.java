package com.mit.community.Task;

import com.mit.community.entity.Device;
import com.mit.community.entity.DeviceReportProblem;
import com.mit.community.entity.DnakeDeviceInfo;
import com.mit.community.service.DeviceReportProblemService;
import com.mit.community.service.DeviceService;
import com.mit.community.service.DnakeDeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Component
public class MenJinDisconnectScan {
    @Autowired
    private DnakeDeviceInfoService dnakeDeviceInfoService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceReportProblemService deviceReportProblemService;

    /**
     * 扫描门禁是否掉线
     */
    @Scheduled(cron = "* 0/10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        List<DnakeDeviceInfo> deviceList = dnakeDeviceInfoService.getAllRecord();
        if (!deviceList.isEmpty()) {
            List<DeviceReportProblem> disconnectScan = new ArrayList<>();
            for (int i=0; i<deviceList.size(); i++) {
                Date sendTime = Date.from(deviceList.get(i).getGmtCreate().toInstant(ZoneOffset.of("+8")));
                long longDate = sendTime.getTime();
                long now = System.currentTimeMillis();
                int min = (int)(now - longDate) / 60000;//相差的分钟数
                if (min > 5) {//大于五分钟
                    DeviceReportProblem deviceReportProblem = new DeviceReportProblem();
                    Device deviceInfo = deviceService.getByDeviceId(deviceList.get(i).getId());//查询该设备详细信息
                    deviceReportProblem.setContent("门禁机设备掉线，请及时排查处置");
                    deviceReportProblem.setProblemType("设备掉线");
                    deviceReportProblem.setAddress(deviceInfo.getCoordinate());
                    deviceReportProblem.setDeviceType(1);
                    deviceReportProblem.setStatus(0);
                    deviceReportProblem.setDevice_id(deviceInfo.getId());
                    disconnectScan.add(deviceReportProblem);
                }
            }
            deviceReportProblemService.saveList(disconnectScan);
        }
    }

}
