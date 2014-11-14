package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.service.BaiduAccountService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by john on 2014-7-30.
 */
@Component("AccountRealTimeReport")
public class AccountRealTimeReport {

    @Resource
    private BaiduAccountService baiduAccountService;

    public List<RealTimeResultType> getAccountRealTimeData(String systemUserName,Long accountId, String _startDate, String _endDate) {
        if (_startDate == null && _endDate != null) {
            Assert.notNull(_startDate, "_startDate must not be null!");
        }
        if (_endDate == null && _startDate != null) {
            Assert.notNull(_endDate, "_endDate must not be null!");
        }

        Date startDate = null, endDate = null;
        if (_startDate == null && _endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, -1);
            startDate = cal.getTime();
            endDate = cal.getTime();
        } else {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d");
            try {
                startDate = sd.parse(_startDate);
                endDate = sd.parse(_endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        ReportService reportService = null;
        try {
            CommonService commonService = getCommonService(baiduAccountService.getBaiduAccountInfoBySystemUserNameAndAcId(systemUserName,accountId));
            if(commonService==null){
                return new ArrayList<>();
            }
            reportService = commonService.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //设置请求参数
        RealTimeRequestType realTimeRequestType = new RealTimeRequestType();
        //指定返回数据类型
        realTimeRequestType.setPerformanceData(Arrays.asList(new String[]{"impression", "click", "ctr", "cost", "cpc", "conversion"}));
        //指定起始时间
        realTimeRequestType.setStartDate(startDate);
        realTimeRequestType.setEndDate(endDate);
        //指定实时数据类型
        realTimeRequestType.setReportType(2);
        realTimeRequestType.setDevice(1);

        //创建请求
        GetRealTimeDataRequest getRealTimeDataRequest = new GetRealTimeDataRequest();
        getRealTimeDataRequest.setRealTimeRequestTypes(realTimeRequestType);
        GetRealTimeDataResponse response = reportService.getRealTimeData(getRealTimeDataRequest);
        if(response==null){
           return new ArrayList<>();
        }
        List<RealTimeResultType> list = response.getRealTimeResultTypes();
        return list;
    }



    private CommonService getCommonService(BaiduAccountInfoEntity baiduAccountInfoEntity) {

        if (baiduAccountInfoEntity == null) {
            return null;
        }

        try {
            return ServiceFactory.getInstance(baiduAccountInfoEntity.getBaiduUserName(), baiduAccountInfoEntity.getBaiduPassword(), baiduAccountInfoEntity.getToken(), null);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
