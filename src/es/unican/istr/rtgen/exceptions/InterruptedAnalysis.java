package es.unican.istr.rtgen.exceptions;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 23/01/2016.
 */
public class InterruptedAnalysis extends Exception {

    public InterruptedAnalysis(String msg){
        super(msg);
    }

    public InterruptedAnalysis(){
        super();
    }

}
