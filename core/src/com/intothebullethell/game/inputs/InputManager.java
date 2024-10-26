package com.intothebullethell.game.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.intothebullethell.game.entities.Jugador;

public class InputManager implements InputProcessor {
    private Jugador jugador;
    private boolean pausaSolicitada;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    public void setPausaSolicitada(boolean pausaSolicitada) {
		this.pausaSolicitada = pausaSolicitada;
	}
	public boolean isPausaSolicitada() {
        return pausaSolicitada;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                setUpPressed(true);
                break;
            case Keys.A:
                setLeftPressed(true);
                break;
            case Keys.D:
               setRightPressed(true);
                break;
            case Keys.S:
                setDownPressed(true);
                break;
            case Keys.R:
                jugador.recargarArma();
                break;
            case Keys.ESCAPE:
                pausaSolicitada = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
            	setUpPressed(false);
                break;
            case Keys.A:
                setLeftPressed(false);
                break;
            case Keys.D:
            	setRightPressed(false);
                break;
            case Keys.S:
                setDownPressed(false);
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            jugador.setDisparando(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            jugador.setDisparando(false);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        jugador.setMousePosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}
	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}
	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}
	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}
	
}

