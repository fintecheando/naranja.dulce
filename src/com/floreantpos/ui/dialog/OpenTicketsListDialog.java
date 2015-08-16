/*
 * OpenTicketsListDialog.java
 *
 * Created on September 8, 2006, 12:04 AM
 */

package com.floreantpos.ui.dialog;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.floreantpos.IconFactory;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.main.Application;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.User;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.ui.views.order.CashierModeNextActionDialog;
import com.floreantpos.ui.views.order.OrderView;

/**
 *
 * @author  MShahriar
 */
public class OpenTicketsListDialog extends POSDialog {
	private List<Ticket> openTickets;
	private OpenTicketListTableModel tableModel;

	/** Creates new form OpenTicketsListDialog */
	public OpenTicketsListDialog() {
		init();
	}

	private void init() {
		initComponents();
		setTitle(com.floreantpos.POSConstants.ACTIVE_TICKETS);
		titlePanel.setTitle(com.floreantpos.POSConstants.ACTIVE_TICKETS_BEFORE_DRAWER_RESET);

		TicketDAO dao = new TicketDAO();

		if (TerminalConfig.isCashierMode()) {
			openTickets = dao.findOpenTicketsForUser(Application.getCurrentUser());
		}
		else {
			openTickets = dao.findOpenTickets();
		}

		tableModel = new OpenTicketListTableModel();
		openTicketListTable.setModel(tableModel);
		openTicketListTable.setDefaultRenderer(Object.class, new TicketTableCellRenderer());
		openTicketListTable.setRowHeight(40);

		selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		openTicketListTable.setSelectionModel(selectionModel);
		
		if (TerminalConfig.isCashierMode()) {
			selectionModel.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					Ticket ticket = openTickets.get(openTicketListTable.getSelectedRow());
					ticket = TicketDAO.getInstance().loadFullTicket(ticket.getId());
					OrderView.getInstance().setCurrentTicket(ticket);

					dispose();
				}
			});
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		titlePanel = new com.floreantpos.ui.TitlePanel();
		transparentPanel1 = new com.floreantpos.swing.TransparentPanel();
		transparentPanel3 = new com.floreantpos.swing.TransparentPanel();

		btnClose = new com.floreantpos.swing.PosButton();
		transparentPanel4 = new com.floreantpos.swing.TransparentPanel();
		btnScrollUp = new com.floreantpos.swing.PosButton();
		btnScrollDown = new com.floreantpos.swing.PosButton();
		transparentPanel2 = new com.floreantpos.swing.TransparentPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		openTicketListTable = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

		transparentPanel1.setLayout(new java.awt.BorderLayout());

		transparentPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		transparentPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));

		if (!TerminalConfig.isCashierMode()) {
			btnVoid = new com.floreantpos.swing.PosButton();
			btnVoid.setText(com.floreantpos.POSConstants.VOID);
			btnVoid.setPreferredSize(new java.awt.Dimension(100, 50));
			btnVoid.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					doVoidTicket(evt);
				}
			});

			transparentPanel3.add(btnVoid);

			btnTransferServer = new com.floreantpos.swing.PosButton();
			btnTransferServer.setText("<html><body><center>TRANSFER<br>SERVER</center></body></html>");
			btnTransferServer.setPreferredSize(new java.awt.Dimension(100, 50));
			btnTransferServer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					doTransferServer(evt);
				}
			});

			transparentPanel3.add(btnTransferServer);
		}

		btnClose.setText(com.floreantpos.POSConstants.CLOSE);
		btnClose.setPreferredSize(new java.awt.Dimension(100, 50));
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doClose(evt);
			}
		});

		transparentPanel3.add(btnClose);

		transparentPanel1.add(transparentPanel3, java.awt.BorderLayout.CENTER);

		transparentPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
		btnScrollUp.setIcon(IconFactory.getIcon("/ui_icons/", "up.png"));
		btnScrollUp.setPreferredSize(new java.awt.Dimension(80, 50));
		btnScrollUp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doScrollUp(evt);
			}
		});

		transparentPanel4.add(btnScrollUp);

		btnScrollDown.setIcon(IconFactory.getIcon("/ui_icons/", "down.png"));
		btnScrollDown.setPreferredSize(new java.awt.Dimension(80, 50));
		btnScrollDown.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doScrollDown(evt);
			}
		});

		transparentPanel4.add(btnScrollDown);

		transparentPanel1.add(transparentPanel4, java.awt.BorderLayout.WEST);

		getContentPane().add(transparentPanel1, java.awt.BorderLayout.SOUTH);

		transparentPanel2.setLayout(new java.awt.BorderLayout(0, 5));

		transparentPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
		openTicketListTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		jScrollPane1.setViewportView(openTicketListTable);

		transparentPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		getContentPane().add(transparentPanel2, java.awt.BorderLayout.CENTER);

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 646) / 2, (screenSize.height - 435) / 2, 646, 435);
	}// </editor-fold>//GEN-END:initComponents

	private void doScrollUp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doScrollUp
		int selectedRow = openTicketListTable.getSelectedRow();
		int rowCount = tableModel.getRowCount();

		if (selectedRow <= 0) {
			selectedRow = rowCount - 1;
		}
		else if (selectedRow > (rowCount - 1)) {
			selectedRow = rowCount - 1;
		}
		else {
			--selectedRow;
		}
		openTicketListTable.transferFocus();
		selectionModel.setLeadSelectionIndex(selectedRow);
		Rectangle cellRect = openTicketListTable.getCellRect(selectedRow, 0, false);
		openTicketListTable.scrollRectToVisible(cellRect);
	}//GEN-LAST:event_doScrollUp

	private void doScrollDown(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doScrollDown
		int selectedRow = openTicketListTable.getSelectedRow();
		int rowCount = tableModel.getRowCount();

		if (selectedRow < 0) {
			selectedRow = 0;
		}
		else if (selectedRow >= rowCount - 1) {
			selectedRow = 0;
		}
		else {
			++selectedRow;
		}
		openTicketListTable.transferFocus();
		selectionModel.setLeadSelectionIndex(selectedRow);
		Rectangle cellRect = openTicketListTable.getCellRect(selectedRow, 0, false);
		openTicketListTable.scrollRectToVisible(cellRect);
	}//GEN-LAST:event_doScrollDown

	private Ticket getSelectedTicket() {
		int row = openTicketListTable.getSelectedRow();
		if (row < 0) {
			POSMessageDialog.showError(com.floreantpos.POSConstants.SELECT_TICKET);
			return null;
		}
		return openTickets.get(row);
	}

	private void doClose(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doClose
		canceled = false;
		dispose();

		if (TerminalConfig.isCashierMode()) {
			String message = "What do you want to do next?";
			CashierModeNextActionDialog dialog = new CashierModeNextActionDialog(message);
			dialog.open();
		}
	}//GEN-LAST:event_doClose

	private void doTransferServer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doTransferServer
		try {
			Ticket ticket = getSelectedTicket();

			if (ticket != null) {
				UserListDialog dialog = new UserListDialog();
				dialog.open();
				if (!dialog.isCanceled()) {
					User selectedUser = dialog.getSelectedUser();
					if (!ticket.getOwner().equals(selectedUser)) {
						ticket.setOwner(selectedUser);
						TicketDAO.getInstance().update(ticket);
						openTicketListTable.repaint();
					}
				}
			}
		} catch (Exception e) {
			POSMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_doTransferServer

	private void doPrintTicket(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doPrintTicket
		JOptionPane.showMessageDialog(this, "To be implemented");
	}//GEN-LAST:event_doPrintTicket

	private void doVoidTicket(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doVoidTicket
		Ticket ticket = getSelectedTicket();
		if (ticket == null) {
			return;
		}

		Ticket ticketToVoid = TicketDAO.getInstance().loadFullTicket(ticket.getId());

		VoidTicketDialog dialog = new VoidTicketDialog();
		dialog.setTicket(ticketToVoid);
		dialog.open();

		if (!dialog.isCanceled()) {
			tableModel.removeTicket(ticketToVoid);
		}
	}//GEN-LAST:event_doVoidTicket

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.floreantpos.swing.PosButton btnClose;
	//    private com.floreantpos.swing.PosButton btnPrint;
	private com.floreantpos.swing.PosButton btnScrollDown;
	private com.floreantpos.swing.PosButton btnScrollUp;
	private com.floreantpos.swing.PosButton btnTransferServer;
	private com.floreantpos.swing.PosButton btnVoid;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable openTicketListTable;
	private com.floreantpos.ui.TitlePanel titlePanel;
	private com.floreantpos.swing.TransparentPanel transparentPanel1;
	private com.floreantpos.swing.TransparentPanel transparentPanel2;
	private com.floreantpos.swing.TransparentPanel transparentPanel3;
	private com.floreantpos.swing.TransparentPanel transparentPanel4;
	// End of variables declaration//GEN-END:variables
	private DefaultListSelectionModel selectionModel;

	class OpenTicketListTableModel extends AbstractTableModel {

		public int getRowCount() {
			if (openTickets == null)
				return 0;

			return openTickets.size();
		}

		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
				case 0:
					return com.floreantpos.POSConstants.TICKET_ID;

				case 1:
					return com.floreantpos.POSConstants.SERVER;

				case 2:
					return com.floreantpos.POSConstants.DATE_TIME;

				case 3:
					return com.floreantpos.POSConstants.TOTAL;
			}
			return null;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if (openTickets == null)
				return null;

			Ticket ticket = openTickets.get(rowIndex);

			switch (columnIndex) {
				case 0:
					return Integer.valueOf(ticket.getId());

				case 1:
					return ticket.getOwner().toString();

				case 2:
					return ticket.getCreateDate();

				case 3:
					return Double.valueOf(ticket.getTotalAmount());

			}
			return null;
		}

		void removeTicket(Ticket ticket) {
			openTickets.remove(ticket);
			fireTableDataChanged();
		}
	}

	class TicketTableCellRenderer extends DefaultTableCellRenderer {
		Font font = getFont().deriveFont(Font.BOLD, 12);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yy hh:mm a");
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String currencySymbol = Application.getCurrencySymbol();

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			label.setFont(font);

			if (value instanceof Date) {
				label.setText(dateFormat.format(value));
				label.setHorizontalAlignment(SwingConstants.CENTER);
			}
			else if (value instanceof Double) {
				label.setText(currencySymbol + decimalFormat.format(((Double) value).doubleValue()));
				label.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			else {
				label.setHorizontalAlignment(SwingConstants.LEFT);
			}
			return label;
		}
	}
}