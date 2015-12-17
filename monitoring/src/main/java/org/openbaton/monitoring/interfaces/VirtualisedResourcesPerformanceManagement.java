package org.openbaton.monitoring.interfaces;

import org.openbaton.catalogue.mano.common.monitoring.ObjectSelection;
import org.openbaton.catalogue.mano.common.monitoring.ThresholdDetails;
import org.openbaton.catalogue.mano.common.monitoring.ThresholdType;
import org.openbaton.catalogue.nfvo.Item;
import org.openbaton.exceptions.MonitoringException;

import java.util.List;

/**
 * Created by mob on 17.11.15.
 */
public interface VirtualisedResourcesPerformanceManagement {

    /*
    * This operation will create a PM job, enabling the NFVO to specify a resource or set of resources,
    * that the VIM is managing, for which it wants to receive performance information.
    * This will allow the requesting NFVO to specify its performance information requirements with the VIM.
    *
    * The NFVO needs to issue a Subscribe request for PerformanceInformationAvailable notifications
    * in order to know when new collected performance information is available.
    */
    String createPMJob(ObjectSelection resourceSelector, List<String> performanceMetric, List<String> performanceMetricGroup, Integer collectionPeriod, Integer reportingPeriod) throws MonitoringException;

    /*
    * This operation will delete one or more PM job(s).
    */
    List<String> deletePMJob(List<String> itemIdsToDelete) throws MonitoringException;

    /*
    * This operation will enable the NFVO to solicit from the VIM the details of one or more PM job(s).
    * This operation is not returning performance reports.
    */
    List<Item> queryPMJob(List<String> hostnames, List<String> metrics, String period) throws MonitoringException;

    /*
    * This operation enables the NFVOs to subscribe for the notifications related
    * to performance information with the VIM. This also enables the NFVO to specify
    * the scope of the subscription in terms of the specific virtual resources to be
    * reported by the VIM using a filter as the input.
    */
    void subscribe();
    /*
    * This operation distributes notifications to subscribers.
    * It is a one-way operation issued by the VIM that cannot be invoked
    * as an operation by the consumer (NFVO). 
    * The following notifications can be notified/sent by this operation: 
    *   - PerformanceInformationAvailableNotification
    *   - ThresholdCrossedNotification
    */
    void notifyInfo();
    /*
    * This operation will allow the NFVO to create a threshold to specify
    * threshold levels on specified performance metric and resource(s) for
    * which notifications will be generated when crossed.
    * Creating a threshold does not trigger collection of metrics.
    * In order for the threshold to be active, there should be a PM job collecting
    * the needed metric for the selected entities.
    */
    String createThreshold(ObjectSelection objectSelector, String performanceMetric, ThresholdType thresholdType, ThresholdDetails thresholdDetails) throws MonitoringException;
    /*
    * This operation will allow the NFVO to delete one or more existing threshold(s).
    */
    List<String> deleteThreshold(List<String> thresholdIds) throws MonitoringException;
    /*
    * This operation will allow the NFVO to query the details of an existing threshold.
    */
    void queryThreshold(String queryFilter);
}
