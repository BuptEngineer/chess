1.基于socket完成了两人联机对弈，包含重开，悔棋，认输，求和，离开等基本功能

2.使用配置文件指定服务器ip和端口号

3.mychess.entity.Server为服务器，该服务器的ip和port在conf/chess.properties指定
	mychess.client.ChessClient为客户端，打开两个此界面对弈

------一些问题------
1.判断是否游戏结束偶尔出现判断错误
2.时间长会出现游戏崩溃
3.在游戏结束的时候悔棋，会出现异常情况
4.多于两个人对弈出现问题
5.当自己走棋的时候，没有提示。同时走棋效果不是很好，吃子、将军提示没有完善。
6.仅仅支持一对进行对弈，没有加入房间功能

All rights reserved by BUPTEngineer
