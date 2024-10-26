package com.intothebullethell.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.intothebullethell.game.globales.ConfiguracionJuego;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(true);
		config.setTitle(ConfiguracionJuego.TITULO);
		config.setWindowedMode(ConfiguracionJuego.ANCHO, ConfiguracionJuego.ALTURA);
		config.setResizable(false);
		new Lwjgl3Application(new IntoTheBulletHell(), config);
	}
}
