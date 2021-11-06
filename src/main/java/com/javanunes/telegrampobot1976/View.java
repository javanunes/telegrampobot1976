/*
 * Um pequeno robô do Telegram no formato cmd do windows feito por
 * javanunes.com@protonmail.com, esse bot permite você falar para a sua sala
 * através dele, interagindo com as pessoas da sala.
 * esse robo nao é profissional, tem muitas falhas, mas funciona para o que eu quero
 * criação: 05/11/2021
 * Para Linux
 */

package com.javanunes.telegrampobot1976;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class View {
    static Controller controle = new Controller();
    public static boolean  ativo = true;
    public static Integer  modoExibicao = 1;
    
    public static void print(Object txt){
        System.out.print(txt);
    } 
    
    public static void alert(String txt){
        JOptionPane.showMessageDialog(null, txt);
    }
    
    public static String gets(){
        Scanner teclado = new Scanner(System.in);
        return teclado.nextLine();
    }
    
    public static void menuAjuda(){
        print("\n\n");
        print("-------AJUDA TELEGRAMPO BOT1976 VERSAO " + Model.getVersao() + "---------------------------\n");
        print("- setup     - > faz a primeira configuracao de sala e token            --\n");
        print("- teste     - > chama funcoes de teste do programador                  --\n");
        print("- cd        - > entra numa sala para enviar texto                      --\n");
        print("- exit      - > sai da sala ou desliga o bot                           --\n");
        print("- aprende!  - > salva ultima resposta para a ultima pergunta feita     --\n");
        print("- debug     - > habilita o modo debug verboso                          --\n");
        print("- dir       - > lista ids de salas                                     --\n");
        print("- CTR + R   - > para finalizar o programa de forma forçada             --\n");
        print("-------------------------------------------------------------------------\n\n"); 
    }
    
    public static void setup(){
        boolean configurou = false;
        String respostaOi = "";
        print("\n\n-------CONFIGURACAO DO TELEGRAMPO BOT1976 VERSAO " + Model.getVersao() + "------\n\n");
        while(!configurou){
            print("Digite o token no formato 123456:xyxyxyx:");
            Model.tokenTelegram = gets();
            print("Digite o id da sala no formato -2424242:");
            Model.salaID = gets();
            print("URL da API, tipo https://api.telegram.org/bot:");
            Model.urlAPI = gets();
            if(Model.urlAPI.isEmpty()){
                Model.urlAPI="https://api.telegram.org/bot";
            }
            print("Quando alguém lhe falar Oi, o que eu devo responder? : ");
            respostaOi = gets();
            Controller.salvarRepostasCampos("Oi", respostaOi);
            
            if(!Model.tokenTelegram.isEmpty() && !Model.salaID.isEmpty() && !respostaOi.isEmpty()){
                configurou = true;
            }
            Controller.salvarValoresCampos();
        }
        View.print("\nPerfeito! digite exit para sair e inicie esse robo de novo\n");
        
    }
    
    public static void interpretadorComandos(String comandos){
        
        String[] meusComandos = comandos.split(" ");
        
        // Se modo 3 de exibição, manda tudo o que for digitado para a sala logada
        if(modoExibicao == 3){
            
            if(meusComandos[0].equals("exit")){
               modoExibicao = 1;
               meusComandos[0] = "";
            }
            if(meusComandos[0].equals("aprende!")){
               Controller.aprender();
               meusComandos[0] = "";
            }
            String arrayToStringNovamente = String.join(" ", meusComandos);
            Controller.mandarMensagemNaSalaLogada(arrayToStringNovamente);
            // TRansformando o array de strings em string novamente
            meusComandos[0] ="";
        }
        
        if(meusComandos[0].equals("debug")){
            Model.debug=true;
            print("Debug verboso ok!\n");
            meusComandos[0] ="";
        }
        if(meusComandos[0].equals("exit")){
            System.exit(0);
        }
        if(meusComandos[0].equals("help")){
            menuAjuda();
        }
        if(meusComandos[0].equals("setup")){
            setup();
        }
        if(meusComandos[0].equals("dir")){
            Controller.listar();
        }
        if(meusComandos[0].equals("aprende!")){
            Controller.aprender();
        }
        if(meusComandos[0].equals("cd")){
            modoExibicao = 3;
            if(meusComandos.length > 1 ){
                Model.salaID = meusComandos[1];
                meusComandos[1]="";
            }
        }
        
        if(meusComandos[0].equals("teste")){
           //
           //String resposta="Gosto muito de chupar }},{ da prova";
           Controller.listar();
          print(Controller.obtemUltimoTextoEnviadoSala()+"\n"); 
          // System.out.println(resposta.indexOf("}},{",0));
          
        }
    }
    
    public static void prompt(){
        String comando;
             
        Controller.iniciar();
        controle.loopingRepostas();
        print(Model.bannerPrompt+"\n");
        while(ativo){
            if(modoExibicao == 1){
              print(Model.prompt1);
            }
            if(modoExibicao == 2){
              print(Model.prompt2);
            }
            if(modoExibicao == 3){
              print(Model.prompt3);
            }
            comando = gets();
            interpretadorComandos(comando);
        }
    }
    
    
    

    
    
    
    
    

// #####################################################################################################################
/*
# AQUI ABAIXO COMEÇA O MAIN, O INICIO DE TUDO:
#    
#     
***********************************************************************************************************************/    
    
    public static void main(String[] args){
        prompt();
    }
    
}
