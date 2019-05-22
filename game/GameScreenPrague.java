package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class GameScreenPrague implements Screen{
    final ITSchoolMeaning game;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture bg_prague;
    Texture lupa;
    Texture itemTexture;
    Texture intro;
    Texture pass;
    Texture pointerTexture;
    Sound itemf;
    Music prague_music;
    Vector3 touchPos;
    Array<Rectangle> items1;
    Rectangle pupa;
    Rectangle passR;
    Rectangle pointer;
    static int itemCount;
    static int levelStage;


    public GameScreenPrague (final ITSchoolMeaning gam){
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        batch = new SpriteBatch();

        touchPos = new Vector3();

        lupa = new Texture("lupa.png");
        itemTexture = new Texture("item.png");
        intro = new Texture("introduction1.png");
        pointerTexture = new Texture("pointer.png");
        pass = new Texture("pass.png");
        bg_prague = new Texture("prague.png");

        itemf = Gdx.audio.newSound(Gdx.files.internal("itemfound.wav"));
        prague_music = Gdx.audio.newMusic(Gdx.files.internal("prague_music.mp3"));

        prague_music.setLooping(true);

        pupa = new Rectangle();
        pupa.x = 1920/2-100/2;
        pupa.y = 1080/9;
        pupa.width =100;
        pupa.height = 100;

        passR = new Rectangle();
        passR.x = 1920/2+40-200/2;
        passR.y = 1080/10;
        passR.width = 200;
        passR.height = 100;

        pointer = new Rectangle();
        pointer.x = 1920/6-50/2;
        pointer.y = 1080/12;
        pointer.width = 50;
        pointer.height = 50;

        items1 = new Array<Rectangle>();
        spawnItems1();
    }

    private void spawnItems1(){
        Rectangle itemR = new Rectangle();
        itemR.x= 1920/2;
        itemR.y=1080/2;
        itemR.height = 25;
        itemR.width = 25;
        items1.add(itemR);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bg_prague, 0, 0);
        game.font1.draw(game.batch, "Items found: " + itemCount, 50, 1080);
        game.batch.draw(lupa, pupa.x, pupa.y);
        for (Rectangle itemR : items1) {
            game.batch.draw(itemTexture, itemR.x, itemR.y);
        }
        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            pupa.x = touchPos.x - 100 / 2;
            pupa.y = touchPos.y - 100 / 2;
        }
        levelStage = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) pupa.x -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) pupa.x += 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) pupa.y -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) pupa.y += 400 * Gdx.graphics.getDeltaTime();

        if (pupa.x < -100 / 2) pupa.x = -100 / 2;
        if (pupa.x > 1920 - 100) pupa.x = 1920 - 100;
        if (pupa.y < -100 / 2) pupa.y = -100 / 2;
        if (pupa.y > 1080 - 100) pupa.y = 1080 / 2;

        Iterator<Rectangle> iter = items1.iterator();
        while (iter.hasNext()) {
            Rectangle itemR1 = iter.next();
            if (itemR1.overlaps(pupa)) {
                itemf.play();
                itemCount++;
                iter.remove();
            }
        }
        if (itemCount == 1 && levelStage == 1) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.batch.draw(intro, 0, 0);
            game.batch.draw(pointerTexture, pointer.x, pointer.y);
            game.batch.draw(pass, passR.x, passR.y);
            game.batch.end();

            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                pointer.x = touchPos.x - 50 / 2;
                pointer.y = touchPos.y - 50 / 2;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                pointer.x -= 400 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                pointer.x += 400 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                pointer.y -= 400 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                pointer.y += 400 * Gdx.graphics.getDeltaTime();

            if (pointer.x < -100 / 2) pointer.x = -100 / 2;
            if (pointer.x > 1920 - 100) pointer.x = 1920 - 100;
            if (pointer.y < -100 / 2) pointer.y = -100 / 2;
            if (pointer.y > 1080 - 100) pointer.y = 1080 / 2;
            if (passR.overlaps(pointer)) {
                itemCount = 0;
                levelStage++;
            }
            if (levelStage == 2 && itemCount == 0) {
                game.setScreen(new GameScreenPotsdam(game));
            }
        }
    }

    @Override
    public void show() {

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
        lupa.dispose();
        itemTexture.dispose();
        intro.dispose();
        bg_prague.dispose();
        prague_music.dispose();
        pointerTexture.dispose();
        itemf.dispose();
        pointerTexture.dispose();
        batch.dispose();
    }
}