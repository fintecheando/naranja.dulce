package com.floreantpos.ui.views;

import com.floreantpos.jasperreport.swing.JRViewer;
import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.sf.jasperreports.engine.JasperPrint;

public class TicketReceiptView extends JPanel {
	JRViewer jrViewer;

	public TicketReceiptView(JasperPrint jasperPrint) {
		setLayout(new BorderLayout());
		
		jrViewer = new JRViewer(jasperPrint);
		jrViewer.setToolbarVisible(false);
		jrViewer.setStatusbarVisible(false);
		
		add(jrViewer);
	}
	
	public JPanel getReportPanel() {
		return jrViewer.getReportPanel();
	}
}
