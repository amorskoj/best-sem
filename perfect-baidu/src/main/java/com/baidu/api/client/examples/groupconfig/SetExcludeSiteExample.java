package com.baidu.api.client.examples.groupconfig;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.ExcludeSiteType;
import com.baidu.api.sem.nms.v2.GroupConfigService;
import com.baidu.api.sem.nms.v2.SetExcludeSiteRequest;
import com.baidu.api.sem.nms.v2.SetExcludeSiteResponse;

import java.rmi.RemoteException;

/**
 * ClassName: SetExcludeSiteExample
 * Function: TODO ADD FUNCTION
 *
 * @author Baidu API Team
 * @date 2012-2-13
 */
public class SetExcludeSiteExample {
    private GroupConfigService service;

    public SetExcludeSiteExample() {
        // Get service factory. Your authentication information will be popped up automatically from
        // baidu-api.properties
        VersionService factory = ServiceFactory.getInstance();
        // Get service stub by given the Service interface.
        // Please see the bean-api.tar.gz to get more details about all the service interfaces.
        this.service = factory.getService(GroupConfigService.class);
    }

    public SetExcludeSiteResponse setExcludeSite() {
        // Prepare your parameters.
        SetExcludeSiteRequest parameters = new SetExcludeSiteRequest();
        ExcludeSiteType excludeSiteConfig = new ExcludeSiteType();
        // Set your group id.
        excludeSiteConfig.setGroupId(2166147);
        // Set your exclude site
        excludeSiteConfig.getExcludeSite().add("7k7k.com");
        excludeSiteConfig.getExcludeSite().add("autohome.com.cn");
        parameters.setExcludeSite(excludeSiteConfig);

        // Invoke the method.
        SetExcludeSiteResponse ret = service.setExcludeSite(parameters);
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
        SetExcludeSiteExample example = new SetExcludeSiteExample();
        example.setExcludeSite();
    }
}
