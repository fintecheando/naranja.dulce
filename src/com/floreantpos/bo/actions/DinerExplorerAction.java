package com.floreantpos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.DinerExplorer;

public class DinerExplorerAction extends AbstractAction {

	public DinerExplorerAction() {
		super(com.floreantpos.POSConstants.DINERS);
	}

	public DinerExplorerAction(String name) {
		super(name);
	}

	public DinerExplorerAction(String name, Icon icon) {
		super(name, icon);
	}

	public void actionPerformed(ActionEvent e) {
		BackOfficeWindow backOfficeWindow = com.floreantpos.util.POSUtil.getBackOfficeWindow();
		
		DinerExplorer explorer = null;
		JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
		int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.DINERS);
		if (index == -1) {
			explorer = new DinerExplorer();
			tabbedPane.addTab(com.floreantpos.POSConstants.DINERS, explorer);
		}
		else {
			explorer = (DinerExplorer) tabbedPane.getComponentAt(index);
		}
		tabbedPane.setSelectedComponent(explorer);
	}

}
