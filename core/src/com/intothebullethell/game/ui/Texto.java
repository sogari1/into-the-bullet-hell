package com.intothebullethell.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.intothebullethell.game.managers.AssetManagerJuego;

public class Texto {
	private BitmapFont bitmapFont;
    private GlyphLayout layout;
    private String fuente = AssetManagerJuego.FUENTE;
    private String text = "";
    private int size = 12;
    private Color color = Color.WHITE;
    private float x = 0,y = 0;

    public Texto(String text) {
        this(text, 12, Color.WHITE, 0, 0);
    }

    public Texto(String text, int size) {
        this(text, size, Color.WHITE, 0, 0);
    }

    public Texto(String text, int size, Color color) {
        this(text, size, color, 0, 0);
    }

    public Texto(String text, int size, Color color, float x, float y) {
        this.text = text;
        this.size = size;
        this.color = color;
        this.x = x;
        this.y = y;
        createBitmapFont();
    }

    private void createBitmapFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(this.fuente));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = this.size;
        parameter.color = this.color;
        this.bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        layout = new GlyphLayout();
    }

    public void draw(SpriteBatch batch) {
        bitmapFont.draw(batch, this.text, this.x, this.y);
    }

    public String getText() {
        return text;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth(){
        layout.setText(bitmapFont, text);
        return layout.width;
    }

    public float getHeight(){
        layout.setText(bitmapFont, text);
        return layout.height;
    }

    public void setFont(String fuente) {
        this.fuente = fuente;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
        this.createBitmapFont();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void centerX() {
        layout.setText(bitmapFont, text);
        this.x = (Gdx.graphics.getWidth() - layout.width) / 2;
    }

    public void centerY() {
        layout.setText(bitmapFont, text);
        this.y = (Gdx.graphics.getHeight() + layout.height) / 2;
    }

    public void centerXY(){
        centerX();
        centerY();
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void dispose(){
        bitmapFont.dispose();
    }
}
