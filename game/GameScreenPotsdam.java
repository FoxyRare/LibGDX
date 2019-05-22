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

public class GameScreenPotsdam implements Screen{
    final ITSchoolMeaning game;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture bg_potsdam;
    Texture bg_vancouver;
    Texture lupa;
    Texture itemTexture;
    Texture pass;
    Texture pointerTexture;
    Sound itemf;
    Music potsdam_music;
    Music vancouver_music;
    Vector3 touchPos;
    Array<Rectangle> items2;
    Rectangle pupa;
    int itemCount;
    static int levelStage;


    public GameScreenPotsdam (final ITSchoolMeaning gam){
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        batch = new SpriteBatch();

        touchPos = new Vector3();

        lupa = new Texture("lupa.png");
        itemTexture = new Texture("item.png");
        pointerTexture = new Texture("pointer.png");
        pass = new Texture("pass.png");
        bg_potsdam = new Texture("potsdam.png");
        bg_vancouver = new Texture("vancouver.png");

        itemf = Gdx.audio.newSound(Gdx.files.internal("itemfound.wav"));
        potsdam_music = Gdx.audio.newMusic(Gdx.files.internal("potsdam_music.mp3"));
        vancouver_music = Gdx.audio.newMusic(Gdx.files.internal("vancouver_music.mp3"));

        potsdam_music.setLooping(true);
        vancouver_music.setLooping(true);

        pupa = new Rectangle();
        pupa.x = 1920/2-100/2;
        pupa.y = 1080/9;
        pupa.width =100;
        pupa.height = 100;

        items2 = new Array<Rectangle>();
        spawnItems2();
    }

    private void spawnItems2(){
        Rectangle itemR = new Rectangle();
        itemR.x= 1920/2;
        itemR.y=1080/2;
        itemR.height = 25;
        itemR.width = 25;
        items2.add(itemR);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bg_potsdam, 0, 0);
        game.font1.draw(game.batch, "Items found: " + itemCount, 50, 1080);
        game.batch.draw(lupa, pupa.x, pupa.y);
        for (Rectangle itemR : items2) {
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

        Iterator<Rectangle> iter = items2.iterator();
        while (iter.hasNext()) {
            Rectangle itemR2 = iter.next();
            if (itemR2.overlaps(pupa)) {
                itemf.play();
                itemCount++;
                iter.remove();
            }
        }
        if (itemCount == 1) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.font1.draw(game.batch, "To be continued", 1920/2, 1080/2);
            game.batch.end();
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
        bg_vancouver.dispose();
        bg_potsdam.dispose();
        potsdam_music.dispose();
        vancouver_music.dispose();
        pointerTexture.dispose();
        itemf.dispose();
        pointerTexture.dispose();
        batch.dispose();
    }
}