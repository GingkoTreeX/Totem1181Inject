热注入dll生成教程

首先使用IDEA或vscode使用gradle构建出该mod
![QQ_1723717566648.png](src%2Fmain%2Fresources%2FImage%2FQQ_1723717566648.png)
将该mod移动至项目的.Loader/LoaderBuilder目录下
运行run.bat
![QQ_1723718522537.png](src%2Fmain%2Fresources%2FImage%2FQQ_1723718522537.png)
最后使用visual studio(管理员运行)   导入该项目并将.Loader/Loader.sln 右键打开解决方案 然后生成  最后生成的.dll文件会出现在.Loader/x64/Debug文件夹下
![QQ_1723718805027.png](src%2Fmain%2Fresources%2FImage%2FQQ_1723718805027.png)
如果build时出现报错就去查看依赖库是否安装，以及是否有管理员权限

若更改了Loader.class的内容 需要将该mod用winrar（或其他解压工具进行关联并解压）根据包路径找到Loader.class 并查看解压前大小（例如4180） 将该数字填写至Loader.h文件最后一行的jsize classLoaderClassSize
然后使用HxD查看工具将Loader.class使用16进制查看并复制全部，此时复制的代码是没有0X前缀的 将复制的内容填写至项目根目录的bytes.txt中 并运行ConsoleApp1.exe便捷添加0X前缀 然后再次复制 将其填入Loader.h的16进制数组中
![QQ_1723718306217.png](src%2Fmain%2Fresources%2FImage%2FQQ_1723718306217.png)