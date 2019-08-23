package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.VisitorInviteCode;
import com.mit.community.mapper.VisitorInviteCodeMapper;
import com.mit.community.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VisitorInviteCodeService {
    @Autowired
    private VisitorInviteCodeMapper visitorInviteCodeMapper;

    public String getInviteCode(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode){
        String message = "";
        String expiryDate = null;
        EntityWrapper<VisitorInviteCode> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        if ("0".equals(dateTag)) {
            expiryDate = getTime();
        } else {
            expiryDate = getFetureDate(1);
        }
        wrapper.eq("expiry_date", expiryDate);
        wrapper.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> list = visitorInviteCodeMapper.selectList(wrapper);


        String inviteCode = "";
        return inviteCode;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    public String getTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        System.out.println("格式化后的日期：" + dateNowStr);
        return dateNowStr;
    }
}
