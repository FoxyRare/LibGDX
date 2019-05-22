package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

    final ITSchoolMeaning game;
    OrthographicCamera camera;
    Texture startButtonTexture;
    Texture exitButtonTexture;
    Texture backGroundTexture;
    Sprite startButtonSprite;
    Sprite exitButtonSprite;
    Sprite backGroundSprite;
    Vector3 temp = new Vector3();

    public MainMenuScreen(final ITSchoolMeaning gam) {
        game = gam;
        float height= Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        startButtonTexture = new Texture(Gdx.files.internal("startButton.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exitButton.png"));
        backGroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        startButtonSprite = new Sprite(startButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        backGroundSprite = new Sprite(backGroundTexture);
        startButtonSprite.setSize(250, 100);
        exitButtonSprite.setSize(250,100);
        backGroundSprite.setSize(width,height);
        startButtonSprite.setPosition(width/2-250/2, height/2+125);
        exitButtonSprite.setPosition(width/2-250/2, height/2-125);
        backGroundSprite.setAlpha(1);
    }
    void handleTouch(){
        if(Gdx.input.justTouched()) {
            temp.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY= temp.y;
            if((touchX>=startButtonSprite.getX()) && touchX<= (startButtonSprite.getX()+startButtonSprite.getWidth()) && (touchY>=startButtonSprite.getY()) && touchY<=(startButtonSprite.getY()+startButtonSprite.getHeight()) ){
                game.setScreen(new GameScreenPrague(game));
            }
            else if((touchX>=exitButtonSprite.getX()) && touchX<= (exitButtonSprite.getX()+exitButtonSprite.getWidth()) && (touchY>=exitButtonSprite.getY()) && touchY<=(exitButtonSprite.getY()+exitButtonSprite.getHeight()) ){
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        backGroundSprite.draw(game.batch);
        startButtonSprite.draw(game.batch);
        exitButtonSprite.draw(game.batch);
        handleTouch();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
    }
}
