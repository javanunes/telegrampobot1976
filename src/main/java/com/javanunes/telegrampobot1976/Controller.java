/*
 * Um pequeno robô do Telegram no formato cmd do windows feito por
 * javanunes.com@protonmail.com, esse bot permite você falar para a sua sala
 * através dele, interagindo com as pessoas da sala.
 * esse robo nao é profissional, tem muitas falhas, mas funciona para o que eu quero
 * criação: 05/11/2021 
 */
package com.javanunes.telegrampobot1976;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Controller {
    
     class ThreadMalditaTravadoraLooping{
         ThreadMalditaTravadoraLooping(){
             String comentaram = null;
             String respostaMinha = null;
             try {
                 while(Model.saudavel){
                    Thread.sleep(Model.intervalo);
                    comentaram = Controller.obtemUltimoTextoEnviadoSala();
                    respostaMinha = Controller.getRepostaPara(comentaram);
                    // se tiver resposta para aquilo
                    if(respostaMinha != null){
                        // se eu não tiver dado essa mesma resposta agora a pouco, ai eu a dou
                        if(!Model.ultimoRepostaDadaPorMim.equals(respostaMinha)){
                             Controller.mandarMensagemNaSalaLogada(respostaMinha);
                        }
                    }
                 }
                 
             } 
             catch (InterruptedException e) {
                 if(!(existe(criaDiretoriosNecessariosRetornaCaminho() + Model.ARQUIVO_RESPOSTAS_MEMORIZADAS))){
                     View.print("Parece ser a primeira vez de uso, digite setup para configurar, saia e tente de novo!");
                     Model.saudavel=  false;
                 }
             }
         }  
     }
    
     public static String obtemHTML(String site){
        try{
            URL url = new URL(site);
            String obtido = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            obtido = in.readLine();   
            while(in.ready()) {
                obtido += in.readLine();   
            }
            in.close();
            if(Model.debug){
                View.print(obtido+"\n");        
            }
            return obtido;
        }
        catch(Exception e){
            if(!existe(criaDiretoriosNecessariosRetornaCaminho() + Model.ARQUIVO_CONFIGURACOES_BASICAS_INICIALIZACAO)){
               View.print("\nBem-vindo(a), falta congurar-me. Digite setup para começar!\n\n"); 
               View.print(Model.prompt1);
               Model.saudavel = false;
               return null;
            }
            View.print("Conexão com o Telegram não funciona, bloqueada ou errada: -> "+e+"\n");
            Model.saudavel = false;
        }
        return null;
     }
      
     public static String getHomeDirUnix(){
        return "/home/" + System.getenv("USER") + "/";
     }  
    
     public static String criaDiretoriosNecessariosRetornaCaminho(){
         String diretorios = getHomeDirUnix() + Model.DIRETORIO_CONFIGURACOES_BOT; 
         try{
            File dirs = new File(diretorios);  
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            return diretorios;
         }
         catch(Exception e){
             View.print("Não foi possível criar os diretorios para abrigar as nossas configuracoes " + diretorios + " pois:" +e+ "\n");
         }
         return "";
     }
     
     public static boolean existe(String arquivo){
         try{
             File arq = new File(arquivo);
             if(arq.isFile()){
                 return true;
             }
             return false;
         }
         catch(Exception e){
             return false;
         }
     }
      
     public static void salvarValoresCampos(){
        String arquivoConfiguracoes = criaDiretoriosNecessariosRetornaCaminho() + Model.ARQUIVO_CONFIGURACOES_BASICAS_INICIALIZACAO;  
        try{     
            if(!existe(arquivoConfiguracoes)){
              if(Model.debug)  
                View.print(arquivoConfiguracoes + " não existe, criando...\n");
            }
            if(!Model.debug)
               View.print("Salvando");
            
            // Se não tem, cria o arquivo
            File arquivo = new File(arquivoConfiguracoes);  
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            
            FileInputStream cfgr = new FileInputStream(arquivoConfiguracoes);
            FileOutputStream cfgw = new FileOutputStream(arquivoConfiguracoes);
            Properties parametros = new Properties();
            if(Model.tokenTelegram == null){
               System.out.println("Token está vazio ou nulo para salvar");
               Model.saudavel = false;
            }    
            parametros.setProperty("token", Model.tokenTelegram);
            parametros.setProperty("salaID", Model.salaID);
            parametros.setProperty("urlAPI", Model.urlAPI);
            parametros.store(cfgw, arquivoConfiguracoes);
            cfgw.close();
        }
        catch(Exception e){
            if(Model.debug){
              View.print("ERRO FS para salvar campos EM "+arquivoConfiguracoes+" "+e+"\n");
            }
            else{
              View.print("Não consegui salvar as configuracões, reinicie!");  
            }
        }
        
     }
    
     public static String getValorCampoArquivoConfiguracao(String campo){
        String arquivoConfiguracoes = criaDiretoriosNecessariosRetornaCaminho() + Model.ARQUIVO_CONFIGURACOES_BASICAS_INICIALIZACAO; 
        try{
            if(!existe(arquivoConfiguracoes)){
               System.out.println(arquivoConfiguracoes + " não existe, criando...");
            }
            FileInputStream cfgr = new FileInputStream(arquivoConfiguracoes);
            Properties parametros = new Properties();
            parametros.load(cfgr);
            cfgr.close();
            return parametros.getProperty(campo);
        }
        catch(Exception e){
            if(Model.debug){
               View.print("Erro ao obter campos do arquivo "+ arquivoConfiguracoes + " pois "+e+"\n");
            }
            else{
               View.print("Se for a primeira vez que abre, digite setup para configurar-me e reinicie!\n");  
            }
            return null;
        }
     }
     
     public static void salvarRepostasCampos(String pergunta, String resposta){
        String arquivoMemorias = criaDiretoriosNecessariosRetornaCaminho() + Model.ARQUIVO_RESPOSTAS_MEMORIZADAS;  
        try{     
            if(!existe(arquivoMemorias)){
                if(Model.debug)
                  View.print(arquivoMemorias + " não existe, criando memorias...");
            }
            if(!Model.debug)
              View.print("Salvando");
            
            // Se não tem, cria o arquivo
            File arquivo = new File(arquivoMemorias);  
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            
            FileInputStream cfgr = new FileInputStream(arquivoMemorias);
            FileOutputStream cfgw = new FileOutputStream(arquivoMemorias);
            Properties parametros = new Properties();
            if(Model.tokenTelegram == null){
               System.out.println("Token está vazio ou nulo para salvar");
               Model.saudavel = false;
            }    
            parametros.setProperty(pergunta, resposta);
           
            parametros.store(cfgw, arquivoMemorias);
            cfgw.close();
        }
        catch(Exception e){
            if(Model.debug){
               View.print("ERRO FS EM "+arquivoMemorias+" "+e);
            }
            else{
               View.print("Memória de repostas sendo criada ainda, reincie! \n"); 
            }
        }
     }
    
     public static String getRepostaPara(String pergunta){
        String arquivoMemorias = criaDiretoriosNecessariosRetornaCaminho() + Model.ARQUIVO_RESPOSTAS_MEMORIZADAS; 
        try{
            if(!existe(arquivoMemorias)){
                if(Model.debug)
                  View.print(arquivoMemorias + " memoria não pode ser lida , arquivo de memoria não existe, criando...\n");
                // Se não tem, cria o arquivo
                File arquivo = new File(arquivoMemorias);  
                if (!arquivo.exists()) {
                    arquivo.createNewFile();
                }
            }
            FileInputStream cfgr = new FileInputStream(arquivoMemorias);
            Properties parametros = new Properties();
            parametros.load(cfgr);
            cfgr.close();
            // se tiver resposta para o texto em pergunta, o retorna
            return parametros.getProperty(pergunta);
        }
        catch(Exception e){
            if(Model.debug){
                View.print("O arquivo de memoria "+ arquivoMemorias + " não existe, erro: " + e); 
            }
            else{
                View.print("O arquivo de memoria "+ arquivoMemorias + " não existe, contruindo...\n"); 
            }
            return null;
        }
     }
     
     public  void loopingRepostas(){
         try{
            Thread responderSozinho = new Thread(new Runnable(){
                @Override
                public void run() {
                  ThreadMalditaTravadoraLooping travadora = new ThreadMalditaTravadoraLooping();  
                }
            });
            responderSozinho.start();
         }
         catch(Exception e){
             View.print("\nOlá! Me ensine algo!\n");
         }
     }
     
     public static void iniciar(){
          Model.tokenTelegram =getValorCampoArquivoConfiguracao("token");
          Model.salaID =getValorCampoArquivoConfiguracao("salaID");
          Model.urlAPI=getValorCampoArquivoConfiguracao("urlAPI"); 
     }
     
     public static void mandarMensagemNaSalaLogada(String txt){
         // https://api.telegram.org/bot<TOKEN>/sendMessage?chat_id=<CHAT_ID>&text=Hello%20World
         if(!txt.isEmpty()){
            txt.replaceAll(" ", "%20");
            if(Model.debug){
                View.print("Chegou p/ mandar ->"+txt+"\n");
            }
            Model.ultimoRepostaDadaPorMim = txt;
            String resposta = obtemHTML(Model.urlAPI + Model.tokenTelegram + "/sendMessage?chat_id=" + Model.salaID + "&text=" + txt);
         }   
     }
     
     public static String formaURLTelegramMetodo(String nomeMetodo){
         return Model.urlAPI + Model.tokenTelegram + "/" + nomeMetodo;
     }
     
     public static String obtemUltimoTextoEnviadoSala(){
         try{
             String url = formaURLTelegramMetodo("getUpdates");
             String resposta = obtemHTML(url);
             Integer posicao = resposta.lastIndexOf(",\"text\":\"");
             Integer posicaoFinal = resposta.indexOf("}", posicao);
             if(Model.debug){
               View.print("\nposicao inicial->"+posicao + "\n");
               View.print("\nposicao final->"+posicaoFinal+ "\n");
               View.print("\ncaracteres entre->"+ (posicao - posicaoFinal) + "\n");
             }
             Model.ultimoComentarioEnviadoPraMim = resposta.substring(posicao+9, posicaoFinal - 1);
             return Model.ultimoComentarioEnviadoPraMim;
         }
         catch(Exception e){
              if(Model.debug){
                   View.print("ERRO ao obter a ultima resposta na sala pois: " + e + " reveja conexões e se a primeira configuração foi feita através de setup.\n");
                   return null;
              }   
         }
         return Model.ultimoComentarioEnviadoPraMim;
     }
     
    public  static void aprender(){
        String existeRepostaParaQuestao = null;
        if(!Model.ultimoComentarioEnviadoPraMim.isEmpty() && !Model.ultimoRepostaDadaPorMim.isEmpty()){
            existeRepostaParaQuestao = getRepostaPara(Model.ultimoComentarioEnviadoPraMim);
            if(existeRepostaParaQuestao == null || existeRepostaParaQuestao.isEmpty()){
                salvarRepostasCampos(Model.ultimoComentarioEnviadoPraMim, Model.ultimoRepostaDadaPorMim);
            }
            else{
                View.print("Ah não, isso eu já sei\n");
            }
        }
    } 
    
    public static void listar(){
        String res = obtemHTML(formaURLTelegramMetodo("getUpdates"));    
        String[] arrayGrande = res.split(","); 
        for(String linha : arrayGrande){
              
            if( linha.contains("chat\":{") || linha.contains("title\":\"") ){
               View.print(linha+"\n");
            }
        }
    }
     
      

      
      
      
      
      
      
      
      
      
      
      
      
} // FIM cONTROLLER
