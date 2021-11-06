/*
 * Um pequeno robô do Telegram no formato cmd do windows feito por
 * javanunes.com@protonmail.com, esse bot permite você falar para a sua sala
 * através dele, interagindo com as pessoas da sala.
 * esse robo nao é profissional, tem muitas falhas, mas funciona para o que eu quero
 * criação: 05/11/2021 
 */
package com.javanunes.telegrampobot1976;


public class Model {
    private static String  VERSION = "1.0.0.1";
    public  static boolean debug = false;
    public  static boolean saudavel = true;
    public  static String  prompt1 = "C:\\>";
    public  static String  prompt2 = "C:\\WINDOWS\\>";
    public  static String  prompt3 = "D:\\>";
    public  static String  prompt4 = "D:\\temp\\>";
    public  static String  bannerPrompt = "\n\n\nMicrosoft Telegrampo Bot 1976 [versão " + VERSION + "]\n(c) 2021 Microzoft Purotion. Todos os seus direitos depravados.\nDeixe esse bot como admin da sua sala!\n\n";
    public  static String  ARQUIVO_CONFIGURACOES_BASICAS_INICIALIZACAO ="config.ini";
    public  static String  ARQUIVO_RESPOSTAS_MEMORIZADAS ="memoria.ini";
    public  static String  DIRETORIO_CONFIGURACOES_BOT=".config/TelegramBot1976/";
    public  static String  salaID="-12345678";
    public  static String  tokenTelegram="923849484:hjhdfghdjgfdfhg";
    public  static String  urlAPI= "https://api.telegram.org/bot";
    public  static String  ultimoComentarioEnviadoPraMim = "";
    public  static String  ultimoRepostaDadaPorMim = "";
    public  static Integer intervalo  = 3600;
    
    public static String getVersao(){
        return VERSION;
    }
    
}
