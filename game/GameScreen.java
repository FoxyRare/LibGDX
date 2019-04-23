package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

public class GameScreen implements Screen{
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture bg_prague;
    Texture bg_potsdam;
    Texture bg_vancouver;
    Texture lupa;
    Texture itemTexture;
    Texture intro;
    Texture pass;
    Texture pointerTexture;
    Sound itemf;
    Music prague_music;
    Music potsdam_music;
    Music vancouver_music;
    Vector3 touchPos;
    Array<Rectangle> items;
    Rectangle pupa;
    Rectangle passR;
    Rectangle itemRect;
    Rectangle pointer;


    public void GameScreen (final ITSchoolMeaning gam){
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);

        batch = new SpriteBatch();

        touchPos = new Vector3();

        lupa = new Texture("lupa.png");
        itemTexture = new Texture("item.png");
        intro = new Texture("introduction1.png");
        pointerTexture = new Texture("pointer.png");
        pass = new Texture("pass.png");
        bg_prague = new Texture("prague.png");
        bg_potsdam = new Texture("potsdam.png");
        bg_vancouver = new Texture("vancouver.png");

        itemf = Gdx.audio.newSound(Gdx.files.internal("itemfound.wav"));
        prague_music = Gdx.audio.newMusic(Gdx.files.internal("prague_music.mp3"));
        potsdam_music = Gdx.audio.newMusic(Gdx.files.internal("potsdam_music.mp3"));
        vancouver_music = Gdx.audio.newMusic(Gdx.files.internal("vancouver_music.mp3"));

        prague_music.setLooping(true);
        prague_music.play();
        potsdam_music.setLooping(true);
        vancouver_music.setLooping(true);

        pupa = new Rectangle();
        pupa.x = 1920/2-100/2;
        pupa.y = 1080/9;
        pupa.width =100;
        pupa.height = 100;

        passR = new Rectangle();
        passR.x = 1920/2-200/2;
        passR.y = 1080/10;
        passR.width = 200;
        passR.height = 100;

        pointer = new Rectangle();
        pointer.x = 1920/2-50/2;
        pointer.y = 1080/10;
        pointer.width = 50;
        pointer.height = 50;

        spawnItems();
    }

    private void spawnItems(){
        Rectangle itemR = new Rectangle();
        itemR.x= 1920/2;
        itemR.y=1080/2;
        itemR.height = 25;
        itemR.width = 25;
        items.add(itemR);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg_prague, 0 , 0);
        batch.draw(lupa, pupa.x, pupa.y);
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

    }
}