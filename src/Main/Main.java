package Main;

import ConsoleInterface.MainMenu;
import Model.Animal.Creation.Concrete.*;
import Controllers.c_Menu;
import Model.playerAI.Concrete.PlayerAI;
import Model.Util.Position;
import View.TestFrame;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

import static ConsoleInterface.AnimalCreation.*;
import static ConsoleInterface.Battle.*;
import static ConsoleInterface.DisplayTools.*;

public class Main {

    /**
     * Entry point
     * @param args
     */
    public static void main(String[] args) {
//        new TestFrame();
        new c_Menu();
//        MainMenu.startMainMenu(); // Console version of the program
    }


}

