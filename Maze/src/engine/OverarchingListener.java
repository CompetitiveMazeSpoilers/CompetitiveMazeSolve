package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OverarchingListener implements KeyListener {

	private MazeFrame parent;
	private int mode;
	private boolean p1C;
	private boolean p2C;
	private boolean p1R;
	private boolean p2R;

	public OverarchingListener(MazeFrame parent) {
		this.parent = parent;
		mode = parent.getMode();
		p1C = false;
		p2C = false;
		p1R = false;
		p2R = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
				p1R = true;
			} else {
				p2R = true;
			}
			break;
		case KeyEvent.VK_CONTROL:
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
				p1C = true;
			} else {
				p2C = true;
			}
			break;
		}
		if (p1C || p2C) {
			parent.resetMaze();
		}
		if (mode == MazeFrame.CPU) {
			if (p1R || p2R) {
				parent.getSolve().requestFocus();
				for (MazeCell[] out : parent.getCells())
					for (MazeCell in : out)
						in.go();
				parent.setStartTime((int) (System.currentTimeMillis()));
				parent.setOn(true);
				new Thread(parent).start();
				return;
			}
		} else if (mode == MazeFrame.P2) {
			if (p1R && p2R) {
				parent.getSolve().requestFocus();
				for (MazeCell[] out : parent.getCells())
					for (MazeCell in : out)
						in.go();
				parent.setOn(true);
				parent.setStartTime((int) (System.currentTimeMillis()));
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
				p1R = false;
			} else {
				p2R = false;
			}
			break;
		case KeyEvent.VK_CONTROL:
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
				p1C = false;
			} else {
				p2C = false;
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
