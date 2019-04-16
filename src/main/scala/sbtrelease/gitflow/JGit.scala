package sbtrelease.gitflow

import java.io.File

import org.eclipse.jgit.lib.{Ref, Repository}
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.api.Git

import scala.collection.JavaConverters._

class JGit(repo: Repository) {
  //Наш текущий коммит
  val currentCommit = repo.resolve("HEAD")
  //наш джигит
  val git = new Git(repo)

  def remoteBranch(branchName: String) = s"/refs/remotes/origin/$branchName"


  /**
    * Вычекать имяветки
    * @param branchName имяветки
    */
  def checkoutExisting(branchName: String): Unit ={
    //пуллим изменения
    git.pull().setRemoteBranchName(remoteBranch(branchName))
    //делаем чекаут в нужную ветку
    git.checkout().setName(branchName).call()
    //todo потестить, че будет, если ветки нет локально
  }

  /**
    * Вычекать и запушить новую ветку
    * @param branchName
    */
  def checkoutNew(branchName: String): Unit = {
    val releaseBranch = git.branchCreate().setName(branchName).call()
    git.push().add(releaseBranch)
    //todo .setCredentialsProvider(new UsernamePasswordCredentialsProvider("Makaruch","Makar049"))
    .call()
  }

  /**
    * Поиск ветки по названию
    * @param name
    */
  def findBranch(name: String): Option[Ref] = {
    repo.getAllRefs.asScala.find(_._1.contains(name)).map(_._2)
  }


  /**
    * Добавить файл в коммит
    * @param file
    */
  def addToIndex(file: File): Unit ={
    git.add().addFilepattern(file.getName)
      .call()
  }

  /**
    * Закоммитить что-т
    * @param message сообщение коммита
    */
  def commit(message: String): Unit = {
    git.commit().setMessage(message).call()
  }

  def push(): Unit ={
    git.push().call()
  }
}


object JGit{
  def apply():JGit = apply(new File(System.getProperty("user.dir")))
  def apply(dir:File):JGit = apply((new FileRepositoryBuilder).findGitDir(dir).build)
  def apply(repo:Repository):JGit = new JGit(repo)

  lazy val WorkingDir = JGit()


}