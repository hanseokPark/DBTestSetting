package kr.or.dgit.db_setting;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class AbstractBtnAction extends AbstractAction {
	private SettingMain settingMain;

	public AbstractBtnAction(String name, SettingMain settingMain) {
		super(name);
		this.settingMain = settingMain;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "초기화":
			settingMain.initial();
			break;
		case "백업":
			settingMain.backupData();
			break;
		case "복원":
			settingMain.loadData();
			break;
		}
	}

}
