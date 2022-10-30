# Esercizio di risoluzione di un merge conflict

**Il tempo massimo in laboratorio per questo esercizio è di _20 minuti_.
Se superato, sospendere l'esercizio e riprenderlo per ultimo!**

Si visiti https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test.
Questo repository contiene due branch: `master` e `feature`

Per ognuna delle seguenti istruzioni, si annoti l'output ottenuto.
Prima di eseguire ogni operazione sul worktree o sul repository,
si verifichi lo stato del repository con `git status`.

1. Si cloni localmente il repository

```shell
git clone https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test
```

2. Ci si assicuri di avere localmente entrambi i branch remoti

```shell
git log --all --graph --oneline
* bed943f (origin/feature) Print author information
| * 8e0f29c (HEAD -> master, origin/master, origin/HEAD) Change HelloWorld to print the number of available processors
|/
* d956df6 Create .gitignore
* 700ee0b Create HelloWorld

git remote -v
origin  https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test (fetch)
origin  https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test (push)
```

3. Si faccia il merge di `feature` dentro `master`, ossia: si posizioni la `HEAD` su `master`
   e da qui si esegua il merge di `feature`

```shell
git merge origin/feature
Auto-merging HelloWorld.java
CONFLICT (content): Merge conflict in HelloWorld.java
Automatic merge failed; fix conflicts and then commit the result.
```

4. Si noti che viene generato un **merge conflict**!

```shell
git diff
diff --cc HelloWorld.java
index 4f5d9b7,ed370d1..0000000
--- a/HelloWorld.java
+++ b/HelloWorld.java
@@@ -1,11 -1,9 +1,17 @@@
  public final class HelloWorld {

+       private static final String AUTHOR = "Danilo Pianini";
+ 
        public static void main(final String[] args) {
++<<<<<<< HEAD
 +              System.out.println("This program is running in a PC with " + procNumber() + " logic processors!");
 +      }
 +
 +      public static int procNumber() {
 +              return Runtime.getRuntime().availableProcessors();
++=======
+               System.out.println("This program has been realised by " + AUTHOR);
++>>>>>>> origin/feature
        }
```

5. Si risolva il merge conflict come segue:
   - Il programma Java risultante deve stampare sia il numero di processori disponibili
     (funzionalità presente su `master`)
     che il nome dell'autore del file
     (funzionalità presente su `feature`)

```shell
vi HelloWorld.java

git diff
diff --cc HelloWorld.java
index 4f5d9b7,ed370d1..0000000
--- a/HelloWorld.java
+++ b/HelloWorld.java
@@@ -1,11 -1,9 +1,14 @@@
  public final class HelloWorld {

+       private static final String AUTHOR = "Danilo Pianini";
+ 
        public static void main(final String[] args) {
+               System.out.println("This program has been realised by " + AUTHOR);
 +              System.out.println("This program is running in a PC with " + procNumber() + " logic processors!");
 +      }
 +
 +      public static int procNumber() {
 +              return Runtime.getRuntime().availableProcessors();
        }

  }

git add HelloWorld.java

git log --all --graph --oneline
* bed943f (origin/feature) Print author information
| * 8e0f29c (HEAD -> master, origin/master, origin/HEAD) Change HelloWorld to print the number of available processors
|/
* d956df6 Create .gitignore
* 700ee0b Create HelloWorld

git status
On branch master
Your branch is up to date with 'origin/master'.

All conflicts fixed but you are still merging.
  (use "git commit" to conclude merge)

Changes to be committed:
modified:   HelloWorld.java

git commit -m "branches merged"
[master 221233a] branches merged

git log --all --graph --oneline
*   221233a (HEAD -> master) branches merged
|\
| * bed943f (origin/feature) Print author information
* | 8e0f29c (origin/master, origin/HEAD) Change HelloWorld to print the number of available processors
|/
* d956df6 Create .gitignore
* 700ee0b Create HelloWorld

git diff bed943f
diff --git a/HelloWorld.java b/HelloWorld.java
index ed370d1..907e030 100644
--- a/HelloWorld.java
+++ b/HelloWorld.java
@@ -4,6 +4,11 @@ public final class HelloWorld {

        public static void main(final String[] args) {
                System.out.println("This program has been realised by " + AUTHOR);
+               System.out.println("This program is running in a PC with " + procNumber() + " logic processors!");
+       }
+
+       public static int procNumber() {
+               return Runtime.getRuntime().availableProcessors();
        }

 }

 git diff 8e0f29c
diff --git a/HelloWorld.java b/HelloWorld.java
index 4f5d9b7..907e030 100644
--- a/HelloWorld.java
+++ b/HelloWorld.java
@@ -1,6 +1,9 @@
 public final class HelloWorld {

+       private static final String AUTHOR = "Danilo Pianini";
+
        public static void main(final String[] args) {
+               System.out.println("This program has been realised by " + AUTHOR);
                System.out.println("This program is running in a PC with " + procNumber() + " logic processors!");
        }
```

6. Si crei un nuovo repository nel proprio github personale

7. Si aggiunga il nuovo repository creato come **remote** e si elenchino i remote

```shell
git remote add personal git@github.com:EnryMarch10/lab06_61.git
git remote -v
origin  https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test (fetch)
origin  https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test (push)
personal        git@github.com:EnryMarch10/lab06_61.git (fetch)
personal        git@github.com:EnryMarch10/lab06_61.git (push)
```

8. Si faccia push del branch `master` sul proprio repository

```shell
git status
On branch master
Your branch is ahead of 'origin/master' by 2 commits.
  (use "git push" to publish your local commits)

nothing to commit, working tree clean

git push personal master
```

9. Si setti il branch remoto `master` del nuovo repository come *upstream* per il proprio branch `master` locale (è già creato di default con il push)

```shell
git log --all --graph --oneline
*   221233a (HEAD -> master, personal/master) branches merged
|\
| * bed943f (origin/feature) Print author information
* | 8e0f29c (origin/master, origin/HEAD) Change HelloWorld to print the number of available processors
|/
* d956df6 Create .gitignore
* 700ee0b Create HelloWorld

git branch --set-upstream-to=personal/master
branch 'master' set up to track 'personal/master'.
```