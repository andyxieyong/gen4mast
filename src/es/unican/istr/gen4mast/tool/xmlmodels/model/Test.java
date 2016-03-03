package es.unican.istr.gen4mast.tool.xmlmodels.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by JuanCTR on 12/11/2015.
 */
public class Test {

    public static void main(String[] args) {

        JAXBContext jaxbContext = null;

        try{

            jaxbContext = JAXBContext.newInstance(MASTMODEL.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            MASTMODEL element = (MASTMODEL) jaxbUnmarshaller.unmarshal(new File("example_5_1_2_2.xml"));

            for (Object o: element.getRegularProcessorOrPacketBasedNetworkOrPrimaryScheduler()){
                if (o instanceof RegularSchedulingServer) {
                    RegularSchedulingServer schedServer = (RegularSchedulingServer) o;

                    if (schedServer.getFixedPriorityPolicy() != null){
                        System.out.println(schedServer.getFixedPriorityPolicy().getThePriority());
                    }
                }
            }


        } catch (JAXBException e){
            e.printStackTrace();
        }
    }

}
