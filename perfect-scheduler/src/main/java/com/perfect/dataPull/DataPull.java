package com.perfect.dataPull;

/**
 * Created by john on 2014/8/22.
 */
public class DataPull {

    /**
     * 每天晚上凌晨1点拉取全账户数据
     */
    public void getFullAccountDataPull() {
        /*AsynchronousReportDAO dao = new AsynchronousReportDAOImpl();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());

        List<String> list = DateUtils.getPeriod(yesterday, yesterday);
        for (String dateStr : list) {
            dao.getAccountReportData(dateStr);
            dao.getCampaignReportData(dateStr);
            dao.getAdgroupReportData(dateStr);
            dao.getCreativeReportData(dateStr);
            dao.getKeywordReportData(dateStr);
            dao.getRegionReportData(dateStr);
        }*/
    }
}
