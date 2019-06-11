# 使用引导

## 相关文件

与官方联系获取**dappx-sdk-android.aar**文件，引入工程内，通常是放在libs目录下。

~~~
dependencies {
    implementation(name: 'dappx-sdk-android', ext: 'aar')
}
~~~

## WebView初始化

在WebView初始化时，加入如下代码：

~~~
DappxBridge dappxBridge = new DappxBridge(context, webView);
webView.addJavascriptInterface(dappxBridge, "dappxBridge");
~~~

## 基本信息

填写自己的基本信息，包括应用名称、应用图标链接。

~~~
SelectParams params = new SelectParams();
params.appName = "Sample Test";
params.appIcon = "http://download.dappx.com/static/assets/images/appicon.png";
~~~

如果只想显示指定的钱包，就加入类似代码：

~~~
params.enabledWallets.add(TokenPocket.getsInstance().id);
params.enabledWallets.add(MeetOne.getsInstance().id);
~~~

## 注入脚本

传入上一步的参数得到脚本，在WebView的**onPageFinished()**方法中注入。

~~~
final String script = dappxBridge.getScript(params);

webView.setWebViewClient(new WebViewClient() {
	@Override
	public void onPageFinished(WebView webView, String url) {
		super.onPageFinished(webView, url);

		webView.loadUrl("javascript: " + script);
	}
});
~~~