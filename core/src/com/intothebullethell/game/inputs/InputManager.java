package com.intothebullethell.game.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.intothebullethell.game.entidades.Jugador;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {
	private Jugador jugador;
    private boolean pausaSolicitada;

    private boolean upPressed, downPressed, leftPressed, rightPressed, recargarPressed, disparandoPressed;
    private boolean upJustPressed, downJustPressed, leftJustPressed, rightJustPressed, disparandoJustPressed;
    private boolean upJustReleased, downJustReleased, leftJustReleased, rightJustReleased, disparandoJustReleased;


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
                upPressed = upJustPressed = true;
                upJustReleased = false;
                break;
            case Keys.A:
                leftPressed = leftJustPressed = true;
                leftJustReleased = false;
                break;
            case Keys.D:
                rightPressed = rightJustPressed = true;
                rightJustReleased = false;
                break;
            case Keys.S:
                downPressed = downJustPressed = true;
                downJustReleased = false;
                break;
            case Keys.R:
                recargarPressed = true;
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
                upPressed = upJustPressed = false;
                upJustReleased = true;
                break;
            case Keys.A:
                leftPressed = leftJustPressed = false;
                leftJustReleased = true;
                break;
            case Keys.D:
                rightPressed = rightJustPressed = false;
                rightJustReleased = true;
                break;
            case Keys.S:
                downPressed = downJustPressed = false;
                downJustReleased = true;
                break;
            case Keys.R:
                recargarPressed = false;
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            disparandoPressed = disparandoJustPressed = true;
            disparandoJustReleased = false;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            disparandoPressed = disparandoJustPressed = false;
            disparandoJustReleased = true;
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

    public boolean isUpJustPressed() {
        if (upJustPressed) {
            upJustPressed = false;
            return true;
        }
        return false;
    }

    public boolean isDownJustPressed() {
        if (downJustPressed) {
            downJustPressed = false;
            return true;
        }
        return false;
    }

    public boolean isLeftJustPressed() {
        if (leftJustPressed) {
            leftJustPressed = false;
            return true;
        }
        return false;
    }

    public boolean isRightJustPressed() {
        if (rightJustPressed) {
            rightJustPressed = false;
            return true;
        }
        return false;
    }

    public boolean isUpJustReleased() {
        if (upJustReleased) {
            upJustReleased = false;
            return true;
        }
        return false;
    }

    public boolean isDownJustReleased() {
        if (downJustReleased) {
            downJustReleased = false;
            return true;
        }
        return false;
    }

    public boolean isLeftJustReleased() {
        if (leftJustReleased) {
            leftJustReleased = false;
            return true;
        }
        return false;
    }

    public boolean isRightJustReleased() {
        if (rightJustReleased) {
            rightJustReleased = false;
            return true;
        }
        return false;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isRecargarPressed() {
    	return recargarPressed;
    }
    public boolean isDisparandoPressed() {
        return disparandoPressed;
    }

    public boolean isDisparandoJustPressed() {
        if (disparandoJustPressed) {
            disparandoJustPressed = false;
            return true;
        }
        return false;
    }

    public boolean isDisparandoJustReleased() {
        if (disparandoJustReleased) {
            disparandoJustReleased = false;
            return true;
        }
        return false;
    }
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}
