/*
 * This file is part of Musicott software.
 *
 * Musicott software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Musicott library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Musicott. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 - 2017 Octavio Calleya
 */

package sample;

import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.stage.Stage;

import java.io.*;

/**
 * Preloader of the application. Shows the progress of the tasks of loading the tracks, the playlists, and the
 * waveforms. <p> If it is the first use of the application a prompt dialog asks the user to enter the location of the
 * application folder. </p>
 *
 * @author Octavio Calleya
 * @version 0.10.1-b
 */
public class MainPreloader extends Preloader {


    private Stage preloaderStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("carga.fxml"));
        AnchorPane rootAnchorPane = loader.load();
        preloaderStage = primaryStage;
        preloaderStage.setOnCloseRequest(Event::consume);
        preloaderStage.setTitle("Musicott");
        preloaderStage.setScene(new Scene(rootAnchorPane));

        preloaderStage.setResizable(false);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        System.out.println("asaa");
        preloaderStage.show();
    }


    /**
     * Shows a window asking the user to enter the location of the application folder. The default location is
     * {@code ~/Music/Musicott}
     */
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println("sss");

       // bar.setProgress(pn.getProgress());
    }
}
