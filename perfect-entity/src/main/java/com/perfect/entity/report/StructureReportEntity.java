package com.perfect.entity.report;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by SubDong on 2014/8/12.
 */
public class StructureReportEntity implements Comparable<StructureReportEntity>{
    @Id
    private String id;

    private String date; //时间

    @Field(value = MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;

    @Field(value = MongoEntityConstants.KEYWORD_ID)
    private Long keywordId;  //关键词ID

    @Field(value = MongoEntityConstants.CREATIVE_ID)
    private Long creativeId;    //创意ID

    @Field(value = MongoEntityConstants.REGION_ID)
    private Long regionId; //地域ID

    @Field(value = "agna")
    private String adgroupName; //单元

    @Field(value = "cid")
    private Long campaignId;//计划id

    @Field(value = "cpna")
    private String campaignName; //计划

    @Field(value = "kwna")
    private String keywordName; //关键字

    @Field(value = "crtl")
    private String creativeTitle; //创意标题

    @Field(value = "des1")
    private String description1;//创意内容1

    @Field(value = "des2")
    private String description2;//创意内容2

    @Field(value = "rgna")
    private String regionName; //地域名称

    @Field(value = "pcis")
    private Integer pcImpression;     //PC展现次数

    @Field(value = "pccli")
    private Integer pcClick;      //PC点击次数

    @Field(value = "pcctr")
    private Double pcCtr;     //PC点击率=点击次数/展现次数

    @Field(value = "pccost")
    private BigDecimal pcCost;        //PC消费

    @Field(value = "pccpc")
    private BigDecimal pcCpc;     //PC平均点击价格=消费/点击次数

    @Field(value = "pccpm")
    private BigDecimal pcCpm;       //PC千次展现消费

    @Field(value = "pccs")
    private Double pcConversion;      //PC转化

    @Field(value = "mis")
    private Integer mobileImpression;//移动展现量

    @Field(value = "mcli")
    private Integer mobileClick;//移动点击两

    @Field(value = "mctr")
    private Double mobileCtr;//移动点击率

    @Field(value = "mcost")
    private BigDecimal mobileCost;//移动消费

    @Field(value = "mcpc")
    private BigDecimal mobileCpc;//移动平均点击价格

    @Field(value = "mcpm")
    private BigDecimal mobileCpm;//移动千次展现消费

    @Field(value = "mcs")
    private Double mobileConversion;//移动转化

    private Date dateRep;//查询时间段

    private int count;//统计数

    private String orderBy;//排序字段

    private int terminal;//投放设备

    private String account;//账户id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public String getCreativeTitle() {
        return creativeTitle;
    }

    public void setCreativeTitle(String creativeTitle) {
        this.creativeTitle = creativeTitle;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getPcImpression() {
        return pcImpression;
    }

    public void setPcImpression(Integer pcImpression) {
        this.pcImpression = pcImpression;
    }

    public Integer getPcClick() {
        return pcClick;
    }

    public void setPcClick(Integer pcClick) {
        this.pcClick = pcClick;
    }

    public Double getPcCtr() {
        return pcCtr;
    }

    public void setPcCtr(Double pcCtr) {
        this.pcCtr = pcCtr;
    }

    public BigDecimal getPcCost() {
        return pcCost;
    }

    public void setPcCost(BigDecimal pcCost) {
        this.pcCost = pcCost;
    }

    public BigDecimal getPcCpc() {
        return pcCpc;
    }

    public void setPcCpc(BigDecimal pcCpc) {
        this.pcCpc = pcCpc;
    }

    public BigDecimal getPcCpm() {
        return pcCpm;
    }

    public void setPcCpm(BigDecimal pcCpm) {
        this.pcCpm = pcCpm;
    }

    public Double getPcConversion() {
        return pcConversion;
    }

    public void setPcConversion(Double pcConversion) {
        this.pcConversion = pcConversion;
    }

    public Integer getMobileImpression() {
        return mobileImpression;
    }

    public void setMobileImpression(Integer mobileImpression) {
        this.mobileImpression = mobileImpression;
    }

    public Integer getMobileClick() {
        return mobileClick;
    }

    public void setMobileClick(Integer mobileClick) {
        this.mobileClick = mobileClick;
    }

    public Double getMobileCtr() {
        return mobileCtr;
    }

    public void setMobileCtr(Double mobileCtr) {
        this.mobileCtr = mobileCtr;
    }

    public BigDecimal getMobileCost() {
        return mobileCost;
    }

    public void setMobileCost(BigDecimal mobileCost) {
        this.mobileCost = mobileCost;
    }

    public BigDecimal getMobileCpc() {
        return mobileCpc;
    }

    public void setMobileCpc(BigDecimal mobileCpc) {
        this.mobileCpc = mobileCpc;
    }

    public BigDecimal getMobileCpm() {
        return mobileCpm;
    }

    public void setMobileCpm(BigDecimal mobileCpm) {
        this.mobileCpm = mobileCpm;
    }

    public Double getMobileConversion() {
        return mobileConversion;
    }

    public void setMobileConversion(Double mobileConversion) {
        this.mobileConversion = mobileConversion;
    }

    public Date getDateRep() {
        return dateRep;
    }

    public void setDateRep(Date dateRep) {
        this.dateRep = dateRep;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public int compareTo(StructureReportEntity o) {
        switch (o.getOrderBy()){
            case "11":
                return this.getDateRep().compareTo(o.getDateRep());
            //展现排序
            case "1":
                if(o.getTerminal() == 2){
                    return this.getMobileImpression().compareTo(o.getMobileImpression());
                }else{
                    return this.getPcImpression().compareTo(o.getPcImpression());
                }
            case "-1":
                //默认展现排序
                if(o.getTerminal() == 2){
                    return o.getMobileImpression().compareTo(this.getMobileImpression());
                }else{
                    return o.getPcImpression().compareTo(this.getPcImpression());
                }
                //点击排序
            case "2":
                if(o.getTerminal() == 2){
                    return this.getMobileClick().compareTo(o.getMobileClick());
                }else{
                    return this.getPcClick().compareTo(o.getPcClick());
                }

            case "-2":
                if(o.getTerminal() == 2){
                    return o.getMobileClick().compareTo(this.getMobileClick());
                }else{
                    return o.getPcClick().compareTo(this.getPcClick());
                }
            //消费排序
            case "3":
                if(o.getTerminal() == 2){
                    return this.getMobileCost().compareTo(o.getMobileCost());
                }else{
                    return this.getPcCost().compareTo(o.getPcCost());
                }
            case "-3":
                if(o.getTerminal() == 2){
                    return o.getMobileCost().compareTo(this.getMobileCost());
                }else{
                    return o.getPcCost().compareTo(this.getPcCost());
                }
            //平均点击价格排序
            case "4":
                if(o.getTerminal() == 2){
                    return this.getMobileCpc().compareTo(o.getMobileCpc());
                }else{
                    return this.getPcCpc().compareTo(o.getPcCpc());
                }
            case "-4":
                if(o.getTerminal() == 2){
                    return o.getMobileCpc().compareTo(this.getMobileCpc());
                }else{
                    return o.getPcCpc().compareTo(this.getPcCpc());
                }
                //点击率排序
            case "5":
                if(o.getTerminal() == 2){
                    return this.getMobileCtr().compareTo(o.getMobileCtr());
                }else{
                    return this.getPcCtr().compareTo(o.getPcCtr());
                }
            case "-5":
                if(o.getTerminal() == 2){
                    return o.getMobileCtr().compareTo(this.getMobileCtr());
                }else{
                    return o.getPcCtr().compareTo(this.getPcCtr());
                }
                //转化排序
            case "6":
                if(o.getTerminal() == 2){
                    return this.getMobileConversion().compareTo(o.getMobileConversion());
                }else{
                    return this.getPcConversion().compareTo(o.getPcConversion());
                }
            case "-6":
                if(o.getTerminal() == 2){
                    return o.getMobileConversion().compareTo(this.getMobileConversion());
                }else{
                    return o.getPcConversion().compareTo(this.getPcConversion());
                }
                //单元排序
            case "7":
                    return this.getAdgroupId().compareTo(o.getAdgroupId());
            case "-7":
                    return o.getAdgroupId().compareTo(this.getAdgroupId());
            //计划排序
            case "8":
                    return this.getCampaignName().compareTo(o.getCampaignName());
            case "-8":
                    return o.getCampaignName().compareTo(this.getCampaignName());
            //关键字排序
            case "9":
                    return this.getKeywordName().compareTo(o.getKeywordName());
            case "-9":
                    return o.getKeywordName().compareTo(this.getKeywordName());
            //地域排序
            case "10":
                return this.getRegionId().compareTo(o.getRegionId());
            case "-10":
                return o.getRegionId().compareTo(this.getRegionId());
            //创意排序
            case "12":
                return this.getCreativeId().compareTo(o.getCreativeId());
            case "-12":
                return o.getCreativeId().compareTo(this.getCreativeId());
            default:
                return o.getDateRep().compareTo(this.getDateRep());
        }
    }
}
