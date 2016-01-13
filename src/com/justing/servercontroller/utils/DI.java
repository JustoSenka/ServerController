package com.justing.servercontroller.utils;

import com.justing.servercontroller.calculation.CalculationImpl;
import com.justing.servercontroller.calculation.Calculation;

/**
 *  Dependency Injector <\p>
 *  Used for injecting dependency for this project.
 *  If you want to use interfaces, get them from this class.
 * 
 * @author JustInG
 */
public class DI {
    
    /**
     * Creates new instance of Calculation interface.
     * @return Calculation interface
     */
    public static Calculation getCalculation(){
        return new CalculationImpl();
    }
    
}
