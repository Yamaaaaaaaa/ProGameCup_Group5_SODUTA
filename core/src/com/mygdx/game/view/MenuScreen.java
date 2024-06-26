package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;

import java.util.PrimitiveIterator;

public class MenuScreen implements Screen {
    // KÍCH THƯỚC NÚT
    private int SIZE_BUTTON_WIDTH = 88;
    private int SIZE_BUTTON_HEIGHT = 88;
    private int SIZE_BUTTON_MUSIC_WIDTH = SIZE_BUTTON_WIDTH/2;
    private int SIZE_BUTTON_MUSIC_HEIGHT = SIZE_BUTTON_HEIGHT/2;
    // CÁC CHỨC NĂNG KHÁC
    SpaceGame spaceGame;
    private OrthographicCamera camera;

    // ÂM THANH
    private Music backgroundMusic;
    private Music clickButtonMusic;

    // HÌNH ẢNH
    Texture background;
    Texture buttonNewGameIdle;
    Texture buttonNewGameHover;
    Texture buttonInforGameIdle;
    Texture buttonInforGameHover;
    Texture buttonRankGameIdle;
    Texture buttonRankGameHover;
    Texture buttonMusicOnIdle;
    Texture buttonMusicOnHover;
    Texture buttonMusicOffIdle;
    Texture buttonMusicOffHover;
    Texture buttonExitIdle;
    Texture buttonExitHover;

    // CHỨC NĂNG CHECK ÂM THANH
    boolean checkSoundButtonPlayOn = false;
    boolean checkSoundButtonInforOn = false;
    boolean checkSoundButtonRankOn = false;

    boolean checkSoundButtonMusicOffOn = false;
    static boolean checkSoundOn = true;
    boolean checkSoundButtonExit = false;

    public MenuScreen(){}
    public MenuScreen(SpaceGame spaceGame){
        this.spaceGame = spaceGame;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/watery-graves-181198.mp3"));
        backgroundMusic.setLooping(true);
        clickButtonMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/clickButton.mp3"));
        background = new Texture("button/Background.png");
        buttonNewGameIdle = new Texture("button/Play-Idle.png");
        buttonNewGameHover = new Texture("button/Play-Hover.png");
        buttonInforGameIdle = new Texture("button/Info-Idle.png");
        buttonInforGameHover = new Texture("button/Info-Hover.png");
        buttonRankGameIdle = new Texture("button/Star-Idle.png");
        buttonRankGameHover = new Texture("button/Star-Hover.png");
        buttonMusicOnIdle = new Texture("button/Music-On-Idle.png");
        buttonMusicOnHover = new Texture("button/Music-On-Hover.png");
        buttonMusicOffIdle = new Texture("button/Music-Off-Idle.png");
        buttonMusicOffHover = new Texture("button/Music-Off-Hover.png");
        buttonExitIdle = new Texture("button/PauseScreen/Exit-Idle@2x.png");
        buttonExitHover = new Texture("button/PauseScreen/Exit-Hover@2x.png");
    }
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        if(checkSoundOn) backgroundMusic.play();
    }

    public void render(float var1){
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);

        spaceGame.getBatch().begin();
        spaceGame.getBatch().draw(background, 0, 0, 800, 800);

        // Button Exit Game
        int xE = 220 + SIZE_BUTTON_WIDTH + 40; // tọa độ x
        int yE = 125; // tọa độ y

        Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPoint); // Chuyển đổi tọa độ

        boolean isTouchingButton = touchPoint.x > xE && touchPoint.x < xE + SIZE_BUTTON_WIDTH &&
                touchPoint.y > yE && touchPoint.y < yE + SIZE_BUTTON_HEIGHT;

        if (isTouchingButton) {
            spaceGame.getBatch().draw(buttonExitHover, xE, yE, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                // lưu kết quả
                this.dispose();
                this.spaceGame.fileHandler.Write_Ranking();
                Gdx.app.exit();
            }
            if(!checkSoundButtonExit && checkSoundOn){
                checkSoundButtonExit = true;
                clickButtonMusic.play();
            }

        } else {
            spaceGame.getBatch().draw(buttonExitIdle, xE, yE, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            checkSoundButtonExit = false;
        }


        // button music
        int x = 0;
        int y = 0;

        Vector3 touchPointMusic = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointMusic); // chuyển tỏa độ
        boolean isTouchingButtonMusic = touchPointMusic.x > x && touchPointMusic.x < x + SIZE_BUTTON_MUSIC_WIDTH &&
                touchPointMusic.y > y && touchPointMusic.y < y + SIZE_BUTTON_MUSIC_HEIGHT;
        if (isTouchingButtonMusic) {
            if(checkSoundOn) spaceGame.getBatch().draw(buttonMusicOnHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            else spaceGame.getBatch().draw(buttonMusicOffHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            if (Gdx.input.justTouched()) {
                this.dispose();

                if(checkSoundOn) {

                    //spaceGame.getBatch().draw(buttonMusicOffHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
                    checkSoundOn = false;
                    backgroundMusic.pause();
                }
                else {
                    //spaceGame.getBatch().draw(buttonMusicOnHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
                    checkSoundOn = true;
                    backgroundMusic.play();
                }
            }
            if(checkSoundOn){
                if(!checkSoundButtonMusicOffOn){
                    clickButtonMusic.play();
                    checkSoundButtonMusicOffOn = true;
                }
            }

        } else {
            if(checkSoundOn) spaceGame.getBatch().draw(buttonMusicOnIdle, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            else spaceGame.getBatch().draw(buttonMusicOffIdle, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            checkSoundButtonMusicOffOn = false;
        }
        // Button play game

        int xPlay = 220 + SIZE_BUTTON_WIDTH + 40; // tọa độ x của nút play
        int yPlay = 425; // tọa độ y của nút play

        Vector3 touchPointPlay = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointPlay); // Chuyển đổi tọa độ

        boolean isTouchingButtonPlay = touchPointPlay.x > xPlay && touchPointPlay.x < xPlay + SIZE_BUTTON_WIDTH &&
                touchPointPlay.y > yPlay && touchPointPlay.y < yPlay + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonPlay) {
            spaceGame.getBatch().draw(buttonNewGameHover, xPlay, yPlay, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                this.dispose();
                backgroundMusic.pause();
                clickButtonMusic.pause();
                spaceGame.setScreen(new MapScreen(spaceGame));
//                String mapPath1 = "basic/map1/Medium_Map.tmx";
//                String mapPath2 = "basic/map2/mediumMap.tmx";
     //           spaceGame.setScreen(new GameScreen(spaceGame, mapPath2,2));
            }
            if(!checkSoundButtonPlayOn && checkSoundOn){
                checkSoundButtonPlayOn = true;
                clickButtonMusic.play();
            }

        } else {
            spaceGame.getBatch().draw(buttonNewGameIdle, xPlay, yPlay, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            checkSoundButtonPlayOn = false;
        }


        // Button infor game
        int xInfor = 220; // tọa độ x của nút infor
        int yInfor = 425; // tọa độ y của nút infor

        Vector3 touchPointInfor = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointInfor); // Chuyển đổi tọa độ

        boolean isTouchingButtonInfor = touchPointInfor.x > xInfor && touchPointInfor.x < xInfor + SIZE_BUTTON_WIDTH &&
                touchPointInfor.y > yInfor && touchPointInfor.y < yInfor + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonInfor) {
            spaceGame.getBatch().draw(buttonInforGameHover, xInfor, yInfor, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                this.dispose();
                clickButtonMusic.pause();
                backgroundMusic.pause();
                spaceGame.setScreen(new InforGameScreen(spaceGame));
            }
            if(!checkSoundButtonInforOn && checkSoundOn){
                checkSoundButtonInforOn = true;
                clickButtonMusic.play();
            }

        } else {
            spaceGame.getBatch().draw(buttonInforGameIdle, xInfor, yInfor, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            checkSoundButtonInforOn = false;
        }

        // Button rank game
        int xRank = 220 + SIZE_BUTTON_WIDTH + 40 + SIZE_BUTTON_WIDTH + 40; // tọa độ x của nút rank
        int yRank = 425; // tọa độ y của nút rank

        Vector3 touchPointRank = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointRank); // Chuyển đổi tọa độ

        boolean isTouchingButtonRank = touchPointInfor.x > xRank && touchPointInfor.x < xRank + SIZE_BUTTON_WIDTH &&
                touchPointInfor.y > yRank && touchPointInfor.y < yRank + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonRank) {
            spaceGame.getBatch().draw(buttonRankGameHover, xRank, yRank, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                this.dispose();
                backgroundMusic.pause();
                spaceGame.setScreen(new RankGameScreen(spaceGame));
            }
            if(!checkSoundButtonRankOn && checkSoundOn){
                checkSoundButtonRankOn = true;
                clickButtonMusic.play();
            }

        } else {
            spaceGame.getBatch().draw(buttonRankGameIdle, xRank, yRank, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            checkSoundButtonRankOn = false;
        }


        spaceGame.getBatch().end();
    }

    public void resize(int var1, int var2){

    }

    public void pause(){

    }

    public void resume(){

    }

    public void hide(){

    }

    public void dispose(){

    }
}