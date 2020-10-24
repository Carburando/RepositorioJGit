package Jgit;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Principal {
    public static void main (String[] args) throws IOException, GitAPIException, URISyntaxException {


        try  {
            String httpUrl = "https://github.com/Carburando/RepositorioJGit.git";
            String localPath = "C:\\Users\\daive\\Desktop\\JgitBranch\\";
            Repository localRepo = new FileRepository(localPath);
            Git git = Git.open(new File(localPath));

            AddCommand add = git.add();

            add.addFilepattern(".").call();
            git.commit().setMessage("Added myfile").call();

            git.branchCreate().setName("Development").call();
            git.branchCreate().setName("Repositorio").call();
            git.branchCreate().setName("Servicios").call();
            git.branchCreate().setName("Controlador").call();
            git.branchCreate().setName("Entidades").call();



            // agregar al repositorio remoto
            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish(httpUrl));

            // Podemos agregar mas configuraciones si es necesario aca
            remoteAddCommand.call();

            // hacer push al remoto:
            File localpath = new File("C:\\Users\\daive\\Desktop\\JgitBranch\\");
            List<Ref> call = git.branchList().call();

            for(Ref ref : call ){
                int i = 1;
                System.out.println("rama" + i);
                i++;
                git = Git.init().setDirectory(localpath).call();
                git.remoteAdd().setUri(new URIish("https://github.com/Carburando/RepositorioJGit.git")).setName("origin").call();
                git.push().setRemote("https://github.com/Carburando/RepositorioJGit.git").setCredentialsProvider(new UsernamePasswordCredentialsProvider("Daivedl","httpsdavid1" )).setPushAll().add(".").call();
                //PushCommand pushCommand = git.push();
                //pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("Daivedl", "httpsdavid1"));
                // podemos agregar mas configuraciones aca si es necesario
                //pushCommand.call();
            }
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }







    }
}

