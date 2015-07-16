package com.baidu.api.client.examples.groupconfig;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.AddInterestInfoRequest;
import com.baidu.api.sem.nms.v2.AddInterestInfoResponse;
import com.baidu.api.sem.nms.v2.GroupConfigService;
import com.baidu.api.sem.nms.v2.GroupInterestInfoType;

import java.rmi.RemoteException;

/**
 * ClassName: AddInterestInfoExample  <br>
 * Function: TODO ADD FUNCTION
 *
 * @author Baidu API Team
 * @date Feb 9, 2012
 */
public class AddInterestInfoExample {

    private GroupConfigService service;

    public AddInterestInfoExample() {
        // Get service factory. Your authentication information will be popped up automatically from
        // baidu-api.properties
        VersionService factory = ServiceFactory.getInstance();
        // Get service stub by given the Service interface.
        // Please see the bean-api.tar.gz to get more details about all the service interfaces.
        this.service = factory.getService(GroupConfigService.class);
    }

    public AddInterestInfoResponse addInterestInfo() {
        // Prepare your parameters.
        AddInterestInfoRequest parameters = new AddInterestInfoRequest();
        GroupInterestInfoType type = new GroupInterestInfoType();
        type.setGroupId(228);
        type.getInterestIds().add(2l);
        type.getInterestIds().add(5l);
        type.getInterestIds().add(35l);
        type.getExceptInterestIds().add(602l);
        parameters.getInterests().add(type);
        // Invoke the method.
        AddInterestInfoResponse ret = service.addInterestInfo(parameters);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("result\n" + ObjToStringUtil.objToString(ret.getResponse()));
            return ret;
        } else {
            throw new ClientBusinessException(rheader, ret);
        }
    }


    /**
     * @param args
     * @throws Throwable
     * @throws RemoteException
     */
    public static void main(String[] args) throws Throwable {
        AddInterestInfoExample example = new AddInterestInfoExample();
        example.addInterestInfo();
    }

}
