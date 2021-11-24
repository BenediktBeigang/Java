package gui;

import spieldaten.Spielobjekt;

public interface KollisionsObjekt {
	int getX();
	int getY();
	int getWidth();
	int getHeight();
	Spielobjekt getDaten();
}
