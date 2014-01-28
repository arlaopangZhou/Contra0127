package com.player.boxplayer.view;
/**
 * 每个RadioButton标题UI需要实现的方法
 * @author richardzhou
 *
 */
public interface TileGroupView {

	/**
	 * 初始化视图
	 */
	public void initView();

	/**
	 *  更新数据
	 */
	public void updateData();

	/**
	 *  销毁视图
	 */
	public void destroy();

	/**
	 *  初始化监听事件
	 */
	public void initListener();
}
