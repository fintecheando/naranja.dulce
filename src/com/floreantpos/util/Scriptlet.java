/* 
 * 
 */
package com.floreantpos.util;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import org.apache.log4j.Logger;
//import org.nibble.util.CantidadALetra;
//import dori.jasper.engine.JRDefaultScriptlet;
//import dori.jasper.engine.JRScriptletException;

/**
 * 
 * 
 */
public class Scriptlet extends JRDefaultScriptlet {
    static Logger logger= Logger.getLogger(Scriptlet.class);

	public String cantidadLetra() throws JRScriptletException{
		/*CantidadALetra conver = new CantidadALetra();
		
		Double total = null;
		logger.debug("Valor:"+this.getVariableValue("TOTAL"));
		total = (Double) this.getVariableValue("TOTAL");
		logger.debug("TOTAL:"+total.doubleValue());
		String result=conver.Letras(total.doubleValue());
		*/
                Double total = null;
		logger.debug("Valor:"+this.getVariableValue("TOTAL"));
		total = (Double) this.getVariableValue("TOTAL");
		logger.debug("TOTAL:"+total.doubleValue());
		return NumberToLetterConverter.convertNumberToLetter(total);
	}

}
