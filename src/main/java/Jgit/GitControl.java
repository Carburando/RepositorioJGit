package Jgit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


import javax.swing.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GitControl {
    private String localPath, remotePath;
    private Git git;

    //Metodo para init
    public void init() throws IOException {
        localPath = "C:\\Users\\daive\\Escritorio\\Carburando UTN";
        remotePath = "https://github.com/Carburando/RepositorioJGit.git";
        Repository localRepo = new FileRepository(localPath + "/.git");
        git = new Git(localRepo);
    }

    //Metodo para crear repositorio
    public void createRepositorio() throws IOException {
        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }

    //Método para clonar
    public void cloneRepositorio() throws IOException, GitAPIException {
        Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
    }

    //Método para agregar un archivo
    public void add() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }

    //Método remote origin
    public void remote() throws URISyntaxException, GitAPIException {
        RemoteAddCommand remoteAddCommand = git.remoteAdd();
        remoteAddCommand.setName("origin");
        remoteAddCommand.setUri(new URIish(remotePath));
        remoteAddCommand.call();
    }

    //Metodo para hacer commit
    public void commit() throws IOException, GitAPIException, JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }

    //Metodo para hacer push
    public void push() throws IOException, JGitInternalException, GitAPIException {
        git.push().call();
    }

    public void pushDos() {
        try {
            git.push().setRemote("origin").add("master").call();
        } catch (
                GitAPIException e) {
            // Add your own logic here, for example:
            System.out.println("Username or password incorrect.");
        }
    }

    //Track origin/master a master (esto es necesario si la clonación de un desnudo de repo)
    public void trackMaster() throws IOException, JGitInternalException, GitAPIException {
        git.branchCreate().setName("master").setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM).setStartPoint("origin/master").setForce(true).call();
    }

    //Metodo para hacer pull
    public void pull() throws IOException, GitAPIException {
        git.pull().call();
    }
    //Metodo para crear rama
    public void crearBranch(String nombre){
        try {
            git.checkout().setCreateBranch(true).setName(nombre).call();
            JOptionPane.showMessageDialog(null,"Se creó la rama "+ git.getRepository().getFullBranch());
        } catch (GitAPIException e) {
            JOptionPane.showMessageDialog(null, "No se pudo crear la rama");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se encontró la rama");
        }
    }

    public void cambiarBranch(String nombre){
        try {
            JOptionPane.showMessageDialog(null,"Usted está en la rama "+ git.getRepository().getFullBranch());
            git.checkout().setName(nombre).call();
            JOptionPane.showMessageDialog(null,"Usted se movió a la rama "+ git.getRepository().getFullBranch());
        } catch (GitAPIException e) {
            JOptionPane.showMessageDialog(null, "La rama no existe");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se encontró la rama");
        }
    }

    public void fusionarBranch(String nombre){
        try{
            ObjectId mergeBase = git.getRepository().resolve(nombre);
            MergeResult merge = git.merge().include(mergeBase).setCommit(true)
                    .setFastForward(MergeCommand.FastForwardMode.NO_FF)
                    .setMessage("Se fusionó "+ nombre)
                    .call();
            JOptionPane.showMessageDialog(null,"Se fusionaron las ramas");
        } catch (GitAPIException e){
            JOptionPane.showMessageDialog(null, "Error al fusionar las ramas");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se encontró la rama");
        }
    }
}
