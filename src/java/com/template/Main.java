package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image; // Importado para gerenciar o ícone

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        // Carrega o arquivo FXML
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        // Cria a cena com o tamanho real definido no seu FXML
        Scene scene = new Scene(loader.load(),600,400);

        stage.setTitle("Cadastro ALunos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        // Inicia a aplicação passando os dados dos Alunos
        launch();
    }
}