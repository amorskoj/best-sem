/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.18.0.3036 modeling language!*/

package com.perfect.autosdk.sms.v3;
import com.perfect.autosdk.common.*;
import java.util.*;

// line 75 "../../../../../../../SDKDemo.ump"
public class GetAdgroupByAdgroupIdRequest
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GetAdgroupByAdgroupIdRequest Attributes
  private List<Long> adgroupIds;
  private Integer extended;

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setExtended(Integer aExtended)
  {
    boolean wasSet = false;
    extended = aExtended;
    wasSet = true;
    return wasSet;
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

  public Integer getExtended()
  {
    return extended;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "extended" + ":" + getExtended()+ "]"
     + outputString;
  }
}