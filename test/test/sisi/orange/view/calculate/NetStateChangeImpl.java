package sisi.orange.view.calculate;

public interface NetStateChangeImpl {
	/**
	 * 网络由连接到断开，关闭通知栏，更新数据，移除线程，服务停止
	 */
	public void ClosingProcess();
	/**
	 * 网络由断开到连接，启动服务
	 */
	public void openingProcess();
	/**
	 * 设置改变的时候处理，更新数据库通知方式和广播通知
	 */
	public void setDataProcess();
}
