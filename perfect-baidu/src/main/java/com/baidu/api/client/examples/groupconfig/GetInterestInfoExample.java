package com.baidu.api.client.examples.groupconfig;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.GetInterestInfoRequest;
import com.baidu.api.sem.nms.v2.GetInterestInfoResponse;
import com.baidu.api.sem.nms.v2.GroupConfigService;

import java.rmi.RemoteException;

/**
 * ClassName: GetInterestInfoExample  <br>
 * Function: TODO ADD FUNCTION
 *
 * @author Baidu API Team
 * @date Feb 9, 2012
 */
public class GetInterestInfoExample {

    private GroupConfigService service;

    public GetInterestInfoExample() {
        // Get service factory. Your authentication information will be popped up automatically from
        // baidu-api.properties
        VersionService factory = ServiceFactory.getInstance();
        // Get service stub by given the Service interface.
        // Please see the bean-api.tar.gz to get more details about all the service interfaces.
        this.service = factory.getService(GroupConfigService.class);
    }

    public GetInterestInfoResponse getInterestInfo(long[] groupIds) {
        // Prepare your parameters.
        GetInterestInfoRequest parameters = new GetInterestInfoRequest();
        for (long groupId : groupIds) {
            parameters.getGroupIds().add(groupId);
        }
        // Invoke the method.
        GetInterestInfoResponse ret = service.getInterestInfo(parameters);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("result\n" + ObjToStringUtil.objToString(ret.getInterestInfos()));
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
        GetInterestInfoExample example = new GetInterestInfoExample();
        long[] groupIds = new long[]{228};
        example.getInterestInfo(groupIds);
    }

}
