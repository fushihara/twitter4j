# https://github.com/fushihara/twitter4j での変更点

## 公式限定のUniversalSearch用APIを追加

[2016/12/11のコミット](https://github.com/fushihara/twitter4j/commit/38a95251ca3f0953ccc9a69af244059da3dd6e19)で追加

公式クライアントでしか許されていなかった一週間以上前の検索が可能。

```
private void searchUniversal(Twitter twitter) throws TwitterException {
    Query query = new Query("from:fushihara until:2010-01-01");
    query.resultType(Query.ResultType.recent);
    query.setCount(100);
    QueryUniversalResult result = twitter.searchUniversal(query);
    List<Status> twitterSerches = result.getTweets();
    for (Status tweet : twitterSerches) {
        System.out.println(tweet.getCreatedAt().toString() + ":" + tweet.getUser().getScreenName() + ":" + tweet.getText());
    }
}
```


# Windows+Powershellでjarファイルをコンパイルする方法

mvnコマンドは使えるようにしておく事

同一ディレクトリに以下のpackage.ps1と、githubから落とした/twitter4/を配置する。
そしてpackage.ps1コマンドを実行すると、/twitter4j-github-out/が作られてjarファイルが配置される。


```
# package.ps1
param([String]$outFolderSuffix="twitter4j-github-out");
pushd $PSScriptRoot
function Main{
	$baseDir = $outFolderSuffix
	Remove-Item -Force -Path $baseDir -Recurse
	mkdir $baseDir
	oneProject -projectName "twitter4j-core"
	oneProject -projectName "twitter4j-appengine"
	oneProject -projectName "twitter4j-async"
	oneProject -projectName "twitter4j-examples"
	oneProject -projectName "twitter4j-http2-support"
	oneProject -projectName "twitter4j-media-support"
	oneProject -projectName "twitter4j-stream"
}
function oneProject{
	param([String]$projectName)
	Copy-Item -Path "./twitter4j/$($projectName)" "$($baseDir)/$($projectName)" -Recurse
	pushd "$($baseDir)/$($projectName)"
	mvn --% clean package -Dmaven.test.skip=true --quiet
	popd
}
Main
popd
```
