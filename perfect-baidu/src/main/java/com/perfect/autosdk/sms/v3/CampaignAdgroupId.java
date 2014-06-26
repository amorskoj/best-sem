/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;

import java.util.List;

// line 29 "../../../../../../../SDKDemo.ump"
public class CampaignAdgroupId
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CampaignAdgroupId Attributes
  private Long campaignId;
  private List<Long> adgroupIds;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCampaignId(Long aCampaignId)
  {
    boolean wasSet = false;
    campaignId = aCampaignId;
    wasSet = true;
    return wasSet;
  }

  public void setAdgroupIds(List<Long> aadgroupIds){
    adgroupIds = aadgroupIds;
  }

  public boolean addAdgroupId(Long aAdgroupId)
  {
    boolean wasAdded = false;
    wasAdded = adgroupIds.add(aAdgroupId);
    return wasAdded;
  }

  public boolean removeAdgroupId(Long aAdgroupId)
  {
    boolean wasRemoved = false;
    wasRemoved = adgroupIds.remove(aAdgroupId);
    return wasRemoved;
  }

  public Long getCampaignId()
  {
    return campaignId;
  }

  public Long getAdgroupId(int index)
  {
    Long aAdgroupId = adgroupIds.get(index);
    return aAdgroupId;
  }

  public List<Long> getAdgroupIds()
  {
    return adgroupIds;
  }

  public int numberOfAdgroupIds()
  {
    int number = adgroupIds.size();
    return number;
  }

  public boolean hasAdgroupIds()
  {
    boolean has = adgroupIds.size() > 0;
    return has;
  }

  public int indexOfAdgroupId(Long aAdgroupId)
  {
    int index = adgroupIds.indexOf(aAdgroupId);
    return index;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "campaignId" + ":" + getCampaignId()+ "]"
     + outputString;
  }
}